$( "#idLooKByName" ).autocomplete({
		    source: function( request, response ) {
		        $.ajax({
		            dataType: "json",
		            type : 'Get',
		            url: '/rest/nombre?q='+$('#idLooKByName').val(),
		            success: function(data) {
		                $('#idLooKByName').removeClass('ui-autocomplete-loading');  
		                // hide loading image
		
		                response( $.map( data, function(item) {
							return {
								value: item.codigo+"-"+item.nombre,
								label: item.nombre+" - cant: "+item.existencia+" - precio: "+item.precio
							}
							
		                }));
		            },
		            error: function(data) {
		                $('#idLooKByName').removeClass('ui-autocomplete-loading');  
		            }
		        });
		    },
		    minLength: 3,
		    open: function() {},
		    close: function(event, ui) {
			history.go();
		},
		    focus: function(event,ui) {},
		    select: function(event, ui) {
		    	let cod = ui.item.value.split("-");
		    	 $.ajax({
			            dataType: "json",
			            type : 'Get',
			            url: '/rest/agregar?q='+cod[0],
			            success: function(data) {
			                $('#idLooKByName').removeClass('ui-autocomplete-loading');  
			                
			            },
			            error: function(data) {
			                $('#idLooKByName').removeClass('ui-autocomplete-loading');  
			            }
			        });
			
		}
		});