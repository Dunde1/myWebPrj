$(document).ready(function (){
    coinPrice();
});

coinUpdate = setInterval(function () {
    coinPrice();
}, 3000);

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
    }).fail(function (error) {
        alert('bitcoin server error');
        alert(JSON.stringify(error));
        window.location.href = '/';
    });
}
