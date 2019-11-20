new CBPFWTabs( document.getElementById( 'tabs' ) );
	$(document).ready( function () {
		var idroom = 0;
		var $status;
		var $1mediabox;
		
		//click room
		$("#section-1 .mediabox").click(function(){
			  $1mediabox = $(this);
			  $status = $(this).find("h6").first();
			  if($status.text() == 'Status: Sẵn sàng'){
					  idroom = parseInt($(this).find(".id_room").first().text());
					  $("#openroom").find("select[name='room']").val(idroom);
					  $('#openroom').modal('show');
					  
			  }
			  else if($status.text() == 'Status: Bận'){
				  idroom = parseInt($(this).find(".id_room").first().text());
				  reloadOrder(idroom);
				  reloadMenuFood(idroom, -1);
		  	  };
		});
		
		// submit form
		$("#formopenroom").submit(function(e) {
		        e.preventDefault(); 
		        var fidroom = idroom;
				$.ajax({
		            type: "POST",
		            url: '/api/openroom',
		            data: $('#formopenroom').serialize(),
		            success: function(data){
		            	$('#success').show();
		            	$('#selected-room').addClass("selected");
		            	$('#none-selected').removeClass("selected");
		            	$status.text("Status: Bận");
		            	reloadOrder(idroom);
		            	reloadMenuFood(idroom, -1);
		            	$($1mediabox).addClass("inactive");
		            },
		            dataType: "json",
		            traditional: true
		        }); 
				$('#openroom').modal('hide');
		});
		
		// click chon food
		$("#section-2 .mediabox").click(function(){
			if(idroom == 0){
				alert("Bạn chưa chọn phòng");}
			else{
				var inputidfood = $(this).children('input').get(0);
				var snumber = $(this).children('h6').get(0);
				var number = parseInt($(snumber).text());
				if(number>0){
					idfood = parseInt($(inputidfood).val());
					reloadMenuFood(idroom, idfood);
					$(snumber).text(number-1)
				}
			}
		});
		
		
		//load lai trang
		function reloadOrder(idroom) {
			stringurl = '/employee/load/content-order-detail/' + idroom;
			$('#room-selected').load(stringurl);
			$('#selected-room').addClass("selected");
            $('#none-selected').removeClass("selected");
		};
		function reloadMenuFood(idroom, idfood) {
			stringurl = '/employee/load/content-menu-food/' + idroom + '/' + idfood;
			$('#menu-food').load(stringurl);
		};
		
		$("#luulai").click(function(){
			$.ajax({
	            type: "POST",
	            url: '/api/save-openroom',
	            data: $('#fbill').serialize(),
	            success: function(data){
	            	alert("Lưu thành công!");
	            },
	            dataType: "json",
	            traditional: true
	        }); 
			
		});
		$("#thanhtoan").click(function(){
			$.ajax({
	            type: "GET",
	            url: '/employee/thanhtoan/' + idroom,
	            success: function(data){
	            	alert("Thanh toán thành công!");
	            	location.reload();
	            	
	            },
	            dataType: "json",
	            traditional: true
	        }); 
			
		});
		$("#inhoadon").click(function(){
			window.open('/employee/print-invoice/' + idroom, '_blank');
			
		});
		$("#xuathoadon").click(function(){
			window.open('/employee/invoice/' + idroom, '_blank');
			
		});
		$(".btn-category").click(function(){
			stringurl = '/employee/load/content-food-with-category/' + $(this).data('id');
			$('#section-2').load(stringurl);
		});
		
		$("#btn-search-room").click(function(){
			console.log("sss");
			var valueroom = $("#search-room").val();
			window.location.href = '/employee/home/'+valueroom;
		});
		$("#btn-search-food").click(function(){
			var valueroom = $("#search-food").val();
			if(valueroom=='')
				valueroom='-1';
			stringurl = '/employee/load/content-food/' + valueroom;
			$('#section-2').load(stringurl);
		});
	});
	