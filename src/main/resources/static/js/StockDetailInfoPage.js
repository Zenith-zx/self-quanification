const ws = new WebSocket('ws://localhost:8080/api/stock/stockMarket/stock/quote');
ws.onmessage = function(event) {
  const stockData = JSON.parse(event.data);
  console.log('股票数据更新：', stockData);
};
