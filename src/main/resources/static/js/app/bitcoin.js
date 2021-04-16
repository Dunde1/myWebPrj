let isStart = false;
let bitcoinLogs;

//매시간마다 코인가격 가져오기
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
        accountRefresh();
        calPL()
        isStart = true;
    }).fail(function (error) {
        alert('bitcoin server error');
        alert(JSON.stringify(error));
    });
}

//계좌 리프레쉬
function accountRefresh(){
    let cash = parseInt($('#cash').text());

    let btc = parseInt($('#btc-reserve').val()) * parseInt($('#btc').text());
    let bch = parseInt($('#bch-reserve').val()) * parseInt($('#bch').text());
    let btg = parseInt($('#btg-reserve').val()) * parseInt($('#btg').text());
    let eos = parseInt($('#eos-reserve').val()) * parseInt($('#eos').text());
    let etc = parseInt($('#etc-reserve').val()) * parseInt($('#etc').text());
    let eth = parseInt($('#eth-reserve').val()) * parseInt($('#eth').text());
    let ltc = parseInt($('#ltc-reserve').val()) * parseInt($('#ltc').text());
    let xrp = parseInt($('#xrp-reserve').val()) * parseInt($('#xrp').text());

    let coinAllValue = btc+bch+btg+eos+etc+eth+ltc+xrp;
    let loan = parseInt($('#loan').text());
    let accountValue = cash+coinAllValue;
    let totalAccountValue = accountValue-loan;

    $('#accountValue').text(accountValue);
    $('#coinAllValue').text(coinAllValue);
    $('#totalAccountValue').text(totalAccountValue);
}

//로그 불러오기
function loadBitcoinLog(){
    $.ajax({
        type: 'POST',
        url: '/bitcoin/api/bitcoinLog/'+$('#user-email').val()
    }).done(function (Log){
        bitcoinLogs = Log;
        if(Log=="") {
            $('#dataTable').DataTable().row.add(['-','-','-','-','-']).draw();
            return
        }
        for (let logs in Log){
            $('#dataTable').DataTable().row.add([Log[logs]['createdDate'],
                Log[logs]['coins'],
                Log[logs]['order'],
                Log[logs]['amount'],
                Log[logs]['value']]).draw();
        }
        logAnalysis();
    }).fail(function (error) {
        alert('Log server error');
        alert(JSON.stringify(error));
        window.location.href = '/';
    });
}

//로그 분석(평단가)
function logAnalysis(){
    let avgs = {"btc":0, "bch":0, "btg":0, "eos":0, "etc":0, "eth":0, "ltc":0, "xrp":0};
    let buyValues = {"btc":0, "bch":0, "btg":0, "eos":0, "etc":0, "eth":0, "ltc":0, "xrp":0};
    let amounts = {"btc":0, "bch":0, "btg":0, "eos":0, "etc":0, "eth":0, "ltc":0, "xrp":0};

    for(let logs in bitcoinLogs){
        let coin = bitcoinLogs[logs]['coins'];
        if(bitcoinLogs[logs]['order']=='매수') {
            amounts[coin] += parseInt(bitcoinLogs[logs]['amount']);
            buyValues[coin] += parseInt(bitcoinLogs[logs]['value']) * parseInt(bitcoinLogs[logs]['amount']);
            avgs[coin] = buyValues[coin] / amounts[coin];
        }else{
            amounts[coin] -= parseInt(bitcoinLogs[logs]['amount']);
            buyValues[coin] -= avgs[coin] * amounts[coin];
        }
    }
    $('#btc-avg').text(avgs['btc']);
    $('#bch-avg').text(avgs['bch']);
    $('#btg-avg').text(avgs['btg']);
    $('#eos-avg').text(avgs['eos']);
    $('#etc-avg').text(avgs['etc']);
    $('#eth-avg').text(avgs['eth']);
    $('#ltc-avg').text(avgs['ltc']);
    $('#xrp-avg').text(avgs['xrp']);
}

//손익 계산
function calPL(){
    $('#btc-PL').text(parseInt($('#btc').text()) - parseInt($('#btc-avg').text()));
    $('#bch-PL').text(parseInt($('#bch').text()) - parseInt($('#bch-avg').text()));
    $('#btg-PL').text(parseInt($('#btg').text()) - parseInt($('#btg-avg').text()));
    $('#eos-PL').text(parseInt($('#eos').text()) - parseInt($('#eos-avg').text()));
    $('#etc-PL').text(parseInt($('#etc').text()) - parseInt($('#etc-avg').text()));
    $('#eth-PL').text(parseInt($('#eth').text()) - parseInt($('#eth-avg').text()));
    $('#ltc-PL').text(parseInt($('#ltc').text()) - parseInt($('#ltc-avg').text()));
    $('#xrp-PL').text(parseInt($('#xrp').text()) - parseInt($('#xrp-avg').text()));
}

//갯수적용
function amountCoin(coin, amount) {
    if(amount='전량'){
        $('#'+coin+'-amount').val($('#'+coin+'-reserve').val());
    }else { //절반
        $('#'+coin+'-amount').val(Math.round($('#'+coin+'-reserve').val()/2));
    }
}

//로그 저장
function saveBitcoinLog(coin, trading, amount, price){
    let d = new Date();
    let month = parseInt(d.getMonth())+1;
    if(month<10) month = '0'+month;
    let datetime = ''+d.getFullYear()+'-'+month+'-'+d.getDate()+'T'+d.getHours()+':'+d.getMinutes()+':'+d.getSeconds();
    $('#dataTable').DataTable().row.add([datetime,coin,trading,amount,price]).draw()
}

//매수매도 버튼 작동
function economy(coin, trading){
    let amount = parseInt($('#'+coin+'-amount').val());

    if(amount==0) return

    let reserveCoin = parseInt($('#'+coin+'-reserve').val());
    let nowPrice = parseInt($('#'+coin).text());
    let totalPrice = amount * nowPrice;
    let nowCash = parseInt($('#cash').text());
    let order;

    if(trading=='buy'){
        if(totalPrice > nowCash){
            alert('소지금이 부족합니다.');
            return
        }
        $('#cash').text(nowCash-totalPrice);
        $('#'+coin+'-reserve').val(reserveCoin+amount);
        order = '매수';

    }else{  //sell
        if(amount > reserveCoin){
            alert('보유코인보다 많습니다.');
            return
        }
        $('#cash').text(nowCash+totalPrice);
        $('#'+coin+'-reserve').val(reserveCoin-amount);
        amount *= -1;
        order = '매도';
    }

    let requestData = [{"userEmail":$('#user-email').val()},
        {
            "cash":$('#cash').text(),
            "coinAmount":$('#'+coin+'-reserve').val(),
            "coins":coin
        },{
            "coins":coin,
            "amount":amount,
            "value":nowPrice
        }]

    $.ajax({
        type: 'POST',
        url: '/bitcoin/api/bitcoinDealUpdate',
        data: JSON.stringify(requestData)
    }).fail(function (error) {
        alert('Main server error');
        alert(JSON.stringify(error));
        return
    });

    saveBitcoinLog(coin, order, amount, nowPrice);
    accountRefresh();
}

$(document).ready(function (){
    coinPrice();
    loadBitcoinLog();
});

coinUpdate = setInterval(function () {
    coinPrice();
}, 10000); //3000