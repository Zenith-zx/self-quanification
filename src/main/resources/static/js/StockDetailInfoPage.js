// WebSocket 连接
const ws = new WebSocket('ws://localhost:8080/api/stock/stockMarket/stock/quote');

// 获取显示数据的 DOM 元素
const stockDataDiv = document.getElementById('stockData');

// WebSocket 连接成功
ws.onopen = function() {
  console.log('WebSocket 连接已建立');
  stockDataDiv.innerHTML += '<p>连接服务器成功！</p>';
};

// 接收消息
ws.onmessage = function(event) {
  const response = JSON.parse(event.data);
  console.log('收到服务器响应：', response);
  //
  // if (response.code === 0) {  // 成功响应
  //   if (response.data) {  // 股票数据更新
  //     updateStockDisplay(response.data);
  //   } else {
  //     console.log('订阅成功');
  //   }
  // } else {  // 错误响应
  //   console.error('错误：', response.msg);
  //   alert('操作失败：' + response.msg);
  // }
};

// 连接关闭
ws.onclose = function() {
  console.log('WebSocket 连接已关闭');
  stockDataDiv.innerHTML += '<p>连接已断开</p>';
};

// 发生错误
ws.onerror = function(error) {
  console.error('WebSocket 错误：', error);
  stockDataDiv.innerHTML += '<p>连接发生错误</p>';
};

// 添加订阅函数
function subscribeStock(stockCode, marketId) {
  if (!stockCode) {
    alert('请输入股票代码');
    return;
  }

  if (ws.readyState === WebSocket.OPEN) {
    const subscribeRequest = {
      stockCode: stockCode,
      marketId: parseInt(marketId)  // 确保 marketId 是数字
    };
    console.log('发送订阅请求：', subscribeRequest);
    ws.send(JSON.stringify(subscribeRequest));
  } else {
    console.error('WebSocket 未连接');
    alert('WebSocket 未连接，请刷新页面重试');
  }
}

// 更新股票数据显示
function updateStockDisplay(data) {
  // 根据实际返回的数据结构调整显示逻辑
  const html = `
        <div class="stock-item">
            <h3>${data.s2c?.basicQotList[0]?.security?.code || '未知'}</h3>
            <p>当前价格: ${data.s2c?.basicQotList[0]?.curPrice || '0'}</p>
            <p>涨跌幅: ${data.s2c?.basicQotList[0]?.changeRate || '0'}%</p>
            <p>更新时间: ${new Date().toLocaleString()}</p>
        </div>
    `;
  stockDataDiv.innerHTML = html;
}