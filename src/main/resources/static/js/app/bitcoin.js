/*
    isStart : 가격정보가 로딩이 되었을때 true가 되어 코인을 구매할 수 있도록 합니다.
    bitcoinLogs : 비트코인 로그의 정보들을 담습니다.
    coins : 코인의 이름을 담은 배열정보입니다.
    fees : 코인을 팔 때 발생하는 수수료 입니다.
 */
let isStart = false;
let bitcoinLogs;
let coins = ["btc","bch","btg","eos","etc","eth","ltc","xrp"];
let fees = 0.0005; //0.05%

/*
    ajax를 통해 서버의 코인의 가격정보를 가져옵니다.
 */
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

/*
    현재 계좌정보를 새로고침합니다.
 */
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
    $('#coinAllValue').text(Math.round(coinAllValue-coinAllValue*fees));
    $('#totalAccountValue').text(totalAccountValue);
}

/*
    ajax를 통하여 현재 이메일을 가진 유저의 비트코인 거래내역을 전부 가져옵니다.
 */
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

/*
    비트코인 거래내역을 통해 계산하여 현재 가지고 있는 코인의 평단가를 계산하여 보여줍니다.
 */
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

/*
    현재 보유한 코인의 평단가와 현재 코인의 가격, 판매했을 때 수수료 등을 계산하여 손익이 얼마가 되는지 계산하여 보여줍니다.
 */
function calPL(){
    for(let idx in coins){
        if($('#'+coins[idx]+'-avg').text()=='-') $('#'+coins[idx]+'-PL').text('-');
        else $('#'+coins[idx]+'-PL').text(Math.round(parseInt($('#'+coins[idx]+'').text()) - parseInt($('#'+coins[idx]+'-avg').text())
            - (parseInt($('#'+coins[idx]+'-avg').text())*fees)));
    }
}

/*
    전량, 혹은 절반 버튼을 클릭하였을 때 현재 보유한 코인의 전량, 혹은 절반의 양만큼 표시합니다.
 */
function amountCoin(coin, amount) {
    if(amount=='전량'){
        $('#'+coin+'-amount').val($('#'+coin+'-reserve').val());
    }else { //절반
        $('#'+coin+'-amount').val(Math.round($('#'+coin+'-reserve').val()/2));
    }
}

/*
    가져온 비트코인 거래내역을 페이지의 데이터테이블을 이용하여 저장하고 보여줍니다.
 */
function saveBitcoinLog(coin, trading, amount, price){
    let d = new Date();
    let month = parseInt(d.getMonth())+1;
    if(month<10) month = '0'+month;
    let datetime = ''+d.getFullYear()+'-'+month+'-'+d.getDate()+'T'+d.getHours()+':'+d.getMinutes()+':'+d.getSeconds();
    amount = Math.abs(amount);

    $('#dataTable').DataTable().row.add([datetime,coin,trading,amount,price]).draw();
    bitcoinLogs.push({'createdDate':datetime, 'coins':coin, 'order':trading, 'amount':amount,'value':price});
}

/*
    사용자가 매수, 혹은 매도를 클릭했을 때 상호작용을 담당합니다.
 */
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
        $('#cash').text(nowCash+(Math.round(totalPrice-totalPrice*fees)));  //수수료 적용
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
        alert(coin+" "+Math.abs(amount)+"개 "+order+"주문"+"완료");
    }).fail(function (error) {
        $('#cash').text(nowCash);
        $('#'+coin+'-reserve').val(reserveCoin);
        alert('Main server error');
        alert(JSON.stringify(error));
        return
    });
}

/*
    페이지가 준비되었을 때 비트코인 현재 가격과 현재 유저의 비트코인 거래내역을 전부 가져옵니다.
 */
$(document).ready(function (){
    coinPrice();
    loadBitcoinLog();
});

/*
    매 1초마다 코인의 가격정보를 가져옵니다.
 */
coinUpdate = setInterval(function () {
    coinPrice();
}, 1000);