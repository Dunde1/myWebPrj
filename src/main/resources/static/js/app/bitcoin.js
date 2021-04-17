let isStart = false;
let bitcoinLogs;
let coins = ["btc","bch","btg","eos","etc","eth","ltc","xrp"];

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
        logAnalysis();
        if(Log=="") return
        for (let logs in Log){
            $('#dataTable').DataTable().row.add([
                Log[logs]['createdDate'],
                Log[logs]['coins'],
                Log[logs]['order'],
                Log[logs]['amount'],
                Log[logs]['value']]).draw();
        }
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
        let amount = parseInt(bitcoinLogs[logs]['amount']);
        let value = parseInt(bitcoinLogs[logs]['value']);

        if(bitcoinLogs[logs]['order']=='매수') {
            amounts[coin] += amount;
            buyValues[coin] += value * amount;
            avgs[coin] = buyValues[coin] / amounts[coin];
        }else{
            amounts[coin] -= amount;
            buyValues[coin] -= avgs[coin] * amount;
        }
    }

    for(let idx in coins){
        if(amounts[coins[idx]]==0) $('#'+coins[idx]+'-avg').text('-');
        else $('#'+coins[idx]+'-avg').text(Math.round(avgs[coins[idx]]*100)/100);
    }
}

//손익 계산
function calPL(){
    if($('#btc-avg').text()=='-') $('#btc-PL').text('-');
    else $('#btc-PL').text(parseInt($('#btc-avg').text()) - parseInt($('#btc').text()));
    if($('#bch-avg').text()=='-') $('#bch-PL').text('-');
    else $('#bch-PL').text(parseInt($('#bch-avg').text()) - parseInt($('#bch').text()));
    if($('#btg-avg').text()=='-') $('#btg-PL').text('-');
    else $('#btg-PL').text(parseInt($('#btg-avg').text()) - parseInt($('#btg').text()));
    if($('#eos-avg').text()=='-') $('#eos-PL').text('-');
    else $('#eos-PL').text(parseInt($('#eos-avg').text()) - parseInt($('#eos').text()));
    if($('#etc-avg').text()=='-') $('#etc-PL').text('-');
    else $('#etc-PL').text(parseInt($('#etc-avg').text()) - parseInt($('#etc').text()));
    if($('#eth-avg').text()=='-') $('#eth-PL').text('-');
    else $('#eth-PL').text(parseInt($('#eth-avg').text()) - parseInt($('#eth').text()));
    if($('#ltc-avg').text()=='-') $('#ltc-PL').text('-');
    else $('#ltc-PL').text(parseInt($('#ltc-avg').text()) - parseInt($('#ltc').text()));
    if($('#xrp-avg').text()=='-') $('#xrp-PL').text('-');
    else $('#xrp-PL').text(parseInt($('#xrp-avg').text()) - parseInt($('#xrp').text()));
}

//갯수적용
function amountCoin(coin, amount) {
    if(amount='전량'){
        $('#'+coin+'-amount').val($('#'+coin+'-reserve').val());
    }else { //절반
        $('#'+coin+'-amount').val(Math.round($('#'+coin+'-reserve').val()/2));
    }
}

//로그화면에 저장
function saveBitcoinLog(coin, trading, amount, price){
    let d = new Date();
    let month = parseInt(d.getMonth())+1;
    if(month<10) month = '0'+month;
    let datetime = ''+d.getFullYear()+'-'+month+'-'+d.getDate()+'T'+d.getHours()+':'+d.getMinutes()+':'+d.getSeconds();
    amount = Math.abs(amount);

    $('#dataTable').DataTable().row.add([datetime,coin,trading,amount,price]).draw();
    bitcoinLogs.push({'createdDate':datetime, 'coins':coin, 'order':trading, 'amount':amount,'value':price});
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

    let requestData = {
        "userEmail":$('#user-email').val(),
        "coins":coin,
        "amount":amount,
        "value":nowPrice,
        "cash":$('#cash').text(),
        "coinAmount":$('#'+coin+'-reserve').val()
    }

    $.ajax({
        type: 'POST',
        url: '/bitcoin/api/bitcoinDealUpdate',
        data: JSON.stringify(requestData),
        contentType: 'application/json; charset=UTF-8'
    }).done(function (data) {
        saveBitcoinLog(coin, order, amount, nowPrice);
        accountRefresh();
        logAnalysis();
        calPL();
        alert(coin+" "+amount+"개 "+order+"주문"+"완료");
    }).fail(function (error) {
        $('#cash').text(nowCash);
        $('#'+coin+'-reserve').val(reserveCoin);
        alert('Main server error');
        alert(JSON.stringify(error));
        return
    });
}

$(document).ready(function (){
    coinPrice();
    loadBitcoinLog();
});

coinUpdate = setInterval(function () {
    coinPrice();
}, 1000);