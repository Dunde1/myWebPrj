$(document).ready(function (){
    load();
});

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

var lotto = {
    init: function () {
        var _this = this;
        $('#btn-lotto').on('click', function () {
            _this.lotto();
        });
    },
    lotto: function () {
        var data = {
            id: $('#user-id').val(),
            email: $('#user-email').val()
        };

        if($('#lotto-isRaffle').val()=='true'){
            $('.lottoBall').detach();
            load();
        }

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