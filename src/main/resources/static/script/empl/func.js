
$(document).ready( function () {
	$(".number-food").change(function(){
			var number = $(this).val();
			var eleid = $(this).parent().find("input[name='id-food-order']").get(0);
			var id = $(eleid).val();
			var eleprice1 = $(this).parent().find("input[name='price']").get(0);
			var eleprice = $(this).parent().parent().find(".price-food").get(0);
			var price = $(eleprice1).val();
			var eleidroomm = $(this).parent().find("input[name='id-room']").get(0);
			var idroomm = $(eleidroomm).val();
			$(eleprice).text(price*number);
			
			$.ajax({
				url: "/employee/changenumberfood/"+id+"/"+number, 
				success: function(){
					stringurl = '/employee/load/content-menu-food/' + idroomm + '/' + -1;
					$('#menu-food').load(stringurl);
				}
			});
	});
	
	$(".tbn-deleorderfood").click(function(){
		var eleid = $(this).parent().parent().find("input[name='id-room']").get(0);
		var idroom = $(eleid).val();
		var eleid3 = $(this).parent().parent().find("input[name='id-food-order']").get(0);
		var id = $(eleid3).val();
		stringurl = '/employee/delete-orderfood/' + idroom + '/' + id;
		$('#menu-food').load(stringurl);
	});
});