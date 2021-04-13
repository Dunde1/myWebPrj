$(document).ready(function (){
    var data = {
        id: $('#user-id').val(),
        email: $('#user-email').val()
    };

    alert($('#user-id').val()+ ' ' + $('#user-email').val())

    if($('#getLottoList').val()=="OK") return

    $.ajax({
        type: 'POST',
        url: 'http://192.168.0.10/mit/lottolist',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data)
    }).done(function (lottoList) {
        alert('로또리스트'+lottoList);
        alert('로또리스트json'+JSON.stringify(lottoList))
        readLotto(lottoList);
    }).fail(function (error) {
        alert('첫번째 에러');
        alert(JSON.stringify(error));
        window.location.href = '/';
    });
});

function readLotto(lottoList) {
    lottoList = "";
    if(lottoList=="") {
        lottoList = [{"lnum1":"X","lnum2":"X","lnum3":"X","lnum4":"X","lnum5":"X","lnum6":"X"}]
    }
    alert(JSON.stringify(lottoList));

    $.ajax({
        type: 'POST',
        url: '/function/lottoList',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(lottoList)
    }).fail(function (error) {
        alert('두번째 에러');
        alert(JSON.stringify(error));
        window.location.href = '/';
    });
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

        $.ajax({
            type: 'POST',
            url: 'http://192.168.0.10/mit/lottolist',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (data) {
            $('#getLottoNumbers').val(data.lnum1 + data.lnum2 + data.lnum3 + data.lnum4 + data.lnum5 + data.lnum6);
        }).fail(function (error) {
            alert(JSON.stringify(error))
        });
    },
}

lotto.init();