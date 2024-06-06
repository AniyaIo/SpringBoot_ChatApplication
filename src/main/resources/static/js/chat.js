"use strict";

let myId;
let peerId;
let baseUrl;

function moveToBottom() {
	const bottom = document.documentElement.scrollHeight - document.documentElement.clientHeight;
	window.scroll(0, bottom);
}

function sendMessage() {
	const message = JSON.stringify({
		senderId: myId,
		receiverId: peerId,
		message: $("#message-area").val()
	});
	
	//非同期に送信
	$.ajax({
		method: "POST",
		url: baseUrl + "/send",
		data: message,
		contentType: "application/json",
		dataType: "json" // TODO: write success case and error case
	})
	.done(function(data){
		//成功時の処理
		console.log("send: " + message);
	})
	.fail(function(data) {
		alert('error');
		// ajaxミスったらアラート。
	});

}

function generateMessageClassTag(innerElement) {
	const openingTag = `<div class="message">`;
	const closingTag = `</div>`;
	return openingTag + innerElement + closingTag;
}

// function generatePeerIconClassTag(id) {
// 	const openingTag = `<img class="peer_icon" src="/image/`;
// 	const closingTag = `" width="45" height="45" loading="lazy" decoding="async"/>`;
// 	return openingTag + id + closingTag;
// }

function generateMessageContentClassTag(sideName, content) {
	const openingTag = `<p class="content ` + sideName + `">`;
	const closingTag = `</p>`;
	return openingTag + content + closingTag;
}

function generateSentDateTimeTag(sentDateTime) {
	const openingTag = `<span class="sent_datetime" hidden>`;
	const closingTag = `</span>`;
	return openingTag + sentDateTime + closingTag;
}

function showMessages(messages) {
	for (let i = 0; i < messages.length; i++) {
		const m = messages[i];
		const sentDateTimeTag = generateSentDateTimeTag(m.sendAt);
		let appendingElement;
		
		if (m.senderId == myId) {
			const messageContentClassTag = generateMessageContentClassTag("my_side", m.text);
			appendingElement = generateMessageClassTag(messageContentClassTag + sentDateTimeTag);	
		} else {
			//画像アイコン取得
			// const peerIconClassTag = generatePeerIconClassTag(peerId);
			const messageContentTag = generateMessageContentClassTag("peer_side", m.text);
			// appendingElement = generateMessageClassTag(peerIconClassTag + messageContentTag + sentDateTimeTag);
			appendingElement = generateMessageClassTag(messageContentTag + sentDateTimeTag);	
		}
		$("#message-list").append(appendingElement);
	}
}

function receiveMessage() {
	const messageItem = $(".message");
	
	messageItem.ready(function() {
		let lastMessageDataTime = messageItem.last().children(".sent_datetime").text();
		if (lastMessageDataTime.length == 0) {
			lastMessageDataTime = null;
		}
	
		console.log(lastMessageDataTime);
		
		const messageRequest = JSON.stringify({
			myId: myId,
			peerId: peerId,
			lastMessageDateTime: lastMessageDataTime
		});
		
		//非同期に受信
		$.ajax({
			method: "POST",
			url: baseUrl + "/receive",
			data: messageRequest,
			contentType: "application/json",
			dataType: "json"
		}).done( function (messages) {
			//console.log("request message");
			
			if (messages.length == 0) {
				return;
			}
			
			console.log(messages);
			
			const windowHight = $(window).height();
			const bottomSpaceTop = $('#bottom-space').offset().top;
			const scrollTop = $(window).scrollTop();
			
			showMessages(messages);
			
			if (scrollTop >= bottomSpaceTop - windowHight) {
				moveToBottom();
			}
		}); // TODO: write error case
	});
}

//各種関数の実行
$(function() {
	myId = $("#my_id").text();
	peerId = $("#peer_id").text();
	baseUrl = "http://" + $(location).attr("host");
	
	// formのURLが指定されていない場合に自身のURLへ再度アクセスしないようにする
	$("form").on("submit", function(e){
		e.preventDefault();
	});
	
	$("#submit-button").click(function(){
		sendMessage();
	});
	
	//1秒ごとに受信処理を実行
	setInterval(receiveMessage, 1000);
	//receiveMessage();

	moveToBottom();
});