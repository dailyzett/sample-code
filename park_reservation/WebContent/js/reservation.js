$(document).ready(function() {
	
	
	$("#reservationForm").submit(function(){
		if($("#inputDate").val() == "없음"){
			alert('예약 날짜를 선택하세요');
			return false;
		}
		
		if($("#peopleCount").val() == "없음"){
			alert('예약 인원을 선택하세요');
			return false;
		}
	});
	
	
	
	
	
	$("input:radio[id=btn01]").click(function() {

		var price = $('#price0').html();
		var people = $("#btn01").val()
		
		if ($("input[id=btn01]:checked").val() == "1") {
			$('#price').html((price * people).toLocaleString());
			$('#peopleCount').val(people);
			$('#inputPrice').val(price * people);
		}
	});

	$("input:radio[id=btn02]").click(function() {

		var price = $('#price0').html();
		var people = $("#btn02").val()

		if ($("input[id=btn02]:checked").val() == "2") {
			$('#price').html((price * people).toLocaleString());
			$('#peopleCount').val(people);
			$('#inputPrice').val(price * people);
		}
	});
	
	$("input:radio[id=btn03]").click(function() {

		var price = $('#price0').html();
		var people = $("#btn03").val()


		if ($("input[id=btn03]:checked").val() == "3") {
			$('#price').html((price * people).toLocaleString());
			$('#peopleCount').val(people);
			$('#inputPrice').val(price * people);
		}
	});
	
	$("input:radio[id=btn04]").click(function() {

		var price = $('#price0').html();
		var people = $("#btn04").val()


		if ($("input[id=btn04]:checked").val() == "4") {
			$('#price').html((price * people).toLocaleString());
			$('#peopleCount').val(people);
			$('#inputPrice').val(price * people);
			
		}
	});
	
	$("input:radio[id=btn05]").click(function() {

		var price = $('#price0').html();
		var people = $("#btn05").val()


		if ($("input[id=btn05]:checked").val() == "5") {
			$('#price').html((price * people).toLocaleString());
			$('#peopleCount').val(people);
			$('#inputPrice').val(price * people);
		}
	});

	$("[id^=btn]").on('click', function(event) {
		var id = $(this).attr("id");
		
		if (id == 'btn0') {
			$('#shelterNDP').html("가야산");
			var dateId = $('#date0').html();
			$('#doe').html(dateId);
			$('#inputDate').val(dateId);
		}

		if (id == 'btn1') {
			$('#shelterNDP').html("가야산");
			var dateId = $('#date1').html();
			$('#doe').html(dateId);
			$('#inputDate').val(dateId);
		}

		if (id == 'btn2') {
			$('#shelterNDP').html("가야산");
			var dateId = $('#date2').html();
			$('#doe').html(dateId);
			$('#inputDate').val(dateId);
		}

		if (id == 'btn3') {
			$('#shelterNDP').html("가야산");
			var dateId = $('#date3').html();
			$('#doe').html(dateId);
			$('#inputDate').val(dateId);
		}

		if (id == 'btn0') {
			$('#shelterNDP').html("가야산");
			var dateId = $('#date0').html();
			$('#doe').html(dateId);
			$('#inputDate').val(dateId);
		}

		if (id == 'btn4') {
			$('#shelterNDP').html("가야산");
			var dateId = $('#date4').html();
			$('#doe').html(dateId);
			$('#inputDate').val(dateId);
		}

		if (id == 'btn5') {
			$('#shelterNDP').html("가야산");
			var dateId = $('#date5').html();
			$('#doe').html(dateId);
			$('#inputDate').val(dateId);
		}

		if (id == 'btn6') {
			$('#shelterNDP').html("가야산");
			var dateId = $('#date6').html();
			$('#doe').html(dateId);
			$('#inputDate').val(dateId);
		}

		if (id == 'btn7') {
			$('#shelterNDP').html("가야산");
			var dateId = $('#date7').html();
			$('#doe').html(dateId);
			$('#inputDate').val(dateId);
		}

		if (id == 'btn8') {
			$('#shelterNDP').html("가야산");
			var dateId = $('#date8').html();
			$('#doe').html(dateId);
			$('#inputDate').val(dateId);
		}

		if (id == 'btn9') {
			$('#shelterNDP').html("가야산");
			var dateId = $('#date9').html();
			$('#doe').html(dateId);
			$('#inputDate').val(dateId);
		}

		if (id == 'btn10') {
			$('#shelterNDP').html("가야산");
			var dateId = $('#date10').html();
			$('#doe').html(dateId);
			$('#inputDate').val(dateId);
		}

		if (id == 'btn11') {
			$('#shelterNDP').html("가야산");
			var dateId = $('#date11').html();
			$('#doe').html(dateId);
			$('#inputDate').val(dateId);
		}

		if (id == 'btn12') {
			$('#shelterNDP').html("가야산");
			var dateId = $('#date12').html();
			$('#doe').html(dateId);
			$('#inputDate').val(dateId);
		}

		if (id == 'btn13') {
			$('#shelterNDP').html("가야산");
			var dateId = $('#date13').html();
			$('#doe').html(dateId);
			$('#inputDate').val(dateId);
		}

		if (id == 'btn14') {
			$('#shelterNDP').html("가야산");
			var dateId = $('#date14').html();
			$('#doe').html(dateId);
			$('#inputDate').val(dateId);
		}

		if (id == 'btn15') {
			$('#shelterNDP').html("가야산");
			var dateId = $('#date15').html();
			$('#doe').html(dateId);
			$('#inputDate').val(dateId);
		}
	});
});