/*
    페이지 로딩 시 타 서버(프로젝트)에 저장된 데이터를 가져옵니다.
*/
$(document).ready(function (){
    load();
});

/*
    타 서버에 보낼 데이터는 로그인 한 유저의 id와 email 입니다.
    보낸 데이터를 토대로 검색하여 최근 내역의 데이터 5개를 가져옵니다.
 */
function load(){
    var data = {
        id: $('#user-id').val(),
        email: $('#user-email').val()
    };

    $.ajax({
        type: 'POST',
        url: 'http://mit4.iptime.org:2780/mit/lottolist',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data)
    }).done(function (lottoList) {
        readLotto(lottoList);
    }).fail(function (error) {
        alert('Lotto추첨 Server 에러');
        alert(JSON.stringify(error));
        window.location.href = '/';
    });
}

/*
    만약 가져온 데이터가 없다면 임시로 데이터를 하나 만들어 저장합니다.
    가져온 데이터를 토대로 화면에 데이터를 띄웁니다.
 */
function readLotto(lottoList) {
    if(lottoList=="") {
        lottoList = [{"lnum1":"0","lnum2":"0","lnum3":"0","lnum4":"0","lnum5":"0","lnum6":"0"}];
    }

    for (var lottos in lottoList) {
        $('#tbody').append('<tr class="lottoBall">');
        $('#tbody').append('<td class="lottoBall" style="text-align: center;font-size: 30px">'+(parseInt(lottos)+1)+'</td>');
        for (var numbers in lottoList[lottos]) {
            $('#tbody').append('<td class="lottoBall" style="text-align: center">'+ballStyle(parseInt(lottoList[lottos][numbers]))+'</td>');
        }
        $('#tbody').append('</tr>');
    }
}

/*
    화면에 로또추첨 버튼을 클릭 할 때 마다 새로운 로또번호가 추첨됩니다.
    추첨된 로또는 화면에 띄워지게 되며 해당 서버에 가장 최근내역으로 데이터가 저장되어 불러오게 됩니다.
 */
var lotto = {
    init: function () {
        var _this = this;
        $('#btn-lotto').on('click', function () {
            if($('#lotto-isRaffle').val()=='true'){
                $('.lottoBall').detach();
                load();
            }
            setTimeout(function(){_this.lotto();}, 100);
        });
    },
    lotto: function () {
        var data = {
            id: $('#user-id').val(),
            email: $('#user-email').val()
        };

        $.ajax({
            type: 'POST',
            url: 'http://mit4.iptime.org:2780/mit/lotto',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (data) {
            $('#lotto-isRaffle').val('true');
            $('#getLottoNumbers').append(ballStyle(parseInt(data['lnum1'])));
            $('#getLottoNumbers').append(ballStyle(parseInt(data['lnum2'])));
            $('#getLottoNumbers').append(ballStyle(parseInt(data['lnum3'])));
            $('#getLottoNumbers').append(ballStyle(parseInt(data['lnum4'])));
            $('#getLottoNumbers').append(ballStyle(parseInt(data['lnum5'])));
            $('#getLottoNumbers').append(ballStyle(parseInt(data['lnum6'])));
        }).fail(function (error) {
            alert(JSON.stringify('Lotto추첨 Server 에러'));
            alert(JSON.stringify(error));
        });
    },
}

/*
    숫자에 따라 볼의 색상과 숫자를 표현합니다.
 */
function ballStyle(number) {
    if(number>=1&&number<=10){
        return '<div class="btn-warning btn-circle mr-1 lottoBall" style="font-size: 20px">'+number+'</div>'
    }else if(number>=11&&number<=20) {
        return '<div class="btn-primary btn-circle mr-1 lottoBall" style="font-size: 20px">'+number+'</div>'
    }else if(number>=21&&number<=30) {
        return '<div class="btn-danger btn-circle mr-1 lottoBall" style="font-size: 20px">'+number+'</div>'
    }else if(number>=31&&number<=40) {
        return '<div class="btn-info btn-circle mr-1 lottoBall" style="font-size: 20px">'+number+'</div>'
    }else if(number>=40){
        return '<div class="btn-success btn-circle mr-1 lottoBall" style="font-size: 20px">'+number+'</div>'
    }else if(number==0){
        return '<div class="btn-success btn-circle mr-1 lottoBall" style="font-size: 20px">X</div>'
    }
}

lotto.init();