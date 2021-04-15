$(document).ready(function (){
    coinPrice();
});

coinUpdate = setInterval(function () {
    coinPrice();
}, 3000);

var isStart = false;
function coinPrice(){
    $.ajax({
        type: 'POST',
        url: '/bitcoin/api/coinInfo',
    }).done(function (bitcoinInfo) {
        $('#btc').text(bitcoinInfo['btcPrice']);
        $('#bch').text(bitcoinInfo['bchPrice']);
        $('#btg').text(bitcoinInfo['btgPrice']);
        $('#eos').text(bitcoinInfo['eosPrice']);
        $('#etc').text(bitcoinInfo['etcPrice']);
        $('#eth').text(bitcoinInfo['ethPrice']);
        $('#ltc').text(bitcoinInfo['ltcPrice']);
        $('#xrp').text(bitcoinInfo['xrpPrice']);
        isStart = true;
    }).fail(function (error) {
        alert('bitcoin server error');
        alert(JSON.stringify(error));
        window.location.href = '/';
    });
}

var open = {
    init : function () {
        if(!isStart) return
        var _this = this;

    }
}
