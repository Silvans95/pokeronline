<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html lang="it" class="h-100">
	<head>
		<jsp:include page="../header.jsp" />
		
		  <link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/jqueryUI/jquery-ui.min.css" />
		<style>
			.ui-autocomplete-loading {
				background: white url("../assets/img/jqueryUI/anim_16x16.gif") right center no-repeat;
			}
			.error_field {
		        color: red; 
		    }
		</style>
		
		<title>Ricerca</title>
	</head>
	<body class="d-flex flex-column h-100">
		<jsp:include page="../navbar.jsp" />
		
		
		
		
		<!-- Begin page content -->
		<main class="flex-shrink-0">
		  <div class="container">
		
				<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
				  ${errorMessage}
				  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
				</div>
				
				<div class='card'>
				    <div class='card-header'>
				        <h5>Ricerca elementi</h5> 
				    </div>
				    <div class='card-body'>
		
							<form method="post" action="${pageContext.request.contextPath }/tavolo/list" class="row g-3">
							
								<div class="col-md-6">
									<label for="denominazione" class="form-label">Denominazione</label>
									<input type="text" name="denominazione" id="denominazione" class="form-control" placeholder="Inserire denominazione" >
								</div>
								
								<div class="col-md-6">
									<label for="dataCreazione" class="form-label">Data di Creazione</label>
	                        		<input class="form-control" id="dataCreazione" type="date" placeholder="dd/MM/yy"
	                            		title="formato : gg/mm/aaaa"  name="dataCreazione" >
								</div>
								
								<div class="col-md-6">
									<label for="cifraMinima" class="form-label">Cifra minima</label>
									<input type="number" name="cifraMinima" id="cifraMinima" class="form-control" placeholder="Inserire cifra minima" >
								</div>
								
								<div class="col-md-6">
									<label for="esperienzaMinima" class="form-label">Esperienza minima</label>
									<input type="number" class="form-control" name="esperienzaMinima" id="esperienzaMinima" placeholder="Inserire esperienza minima" >
								</div>
								
									<div class="col-md-6">
										<label for="utenteCreatoreSearchInput" class="form-label">Creatore tavolo:</label>
										<input class="form-control " type="text" id="utenteCreatoreInputId"
												name="utenteCreatoreInput" >
										<input type="hidden" name="utenteCreatore.id" id="utenteCreatoreId" >
									</div>
								
								<div class="col-md-6">
									<label for="giocatoreCercatoSearchInput" class="form-label">Giocatori:</label>
									<input class="form-control " type="text" id="giocatoreCercatoInputId"
											name="giocatoreCercatoInput" >
									<input type="hidden" name="giocatoreCercato.id" id="giocatoreCercatoId" >
								</div>
	
							<div class="col-12">	
								<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
							</div>
										
						</form>
						
						
						<script>
								$("#utenteCreatoreInputId").autocomplete({
									 source: function(request, response) {
									        $.ajax({
									            url: "${pageContext.request.contextPath }/user/searchUtentiAjax",
									            datatype: "json",
									            data: {
									                term: request.term,   
									            },
									            success: function(data) {
									                response($.map(data, function(item) {
									                    return {
										                    label: item.label,
										                    value: item.value
									                    }
									                }))
									            }
									        })
									    },
									//quando seleziono la voce nel campo deve valorizzarsi la descrizione
								    focus: function(event, ui) {
								        $("#utenteCreatoreInputId").val(ui.item.label)
								        return false
								    },
								    minLength: 2,
								    //quando seleziono la voce nel campo hidden deve valorizzarsi l'id
								    select: function( event, ui ) {
								    	$('#utenteCreatoreId').val(ui.item.value);
								    	//console.log($('#registaId').val())
								        return false;
								    }
								});
							</script>
						
						
					<script>
								$("#giocatoreCercatoInputId").autocomplete({
									 source: function(request, response) {
									        $.ajax({
									            url: "${pageContext.request.contextPath }/user/searchUtentiAjax",
									            datatype: "json",
									            data: {
									                term: request.term,   
									            },
									            success: function(data) {
									                response($.map(data, function(item) {
									                    return {
										                    label: item.label,
										                    value: item.value
									                    }
									                }))
									            }
									        })
									    },
									//quando seleziono la voce nel campo deve valorizzarsi la descrizione
								    focus: function(event, ui) {
								        $("#giocatoreCercatoInputId").val(ui.item.label)
								        return false
								    },
								    minLength: 2,
								    //quando seleziono la voce nel campo hidden deve valorizzarsi l'id
								    select: function( event, ui ) {
								    	$('#giocatoreCercatoId').val(ui.item.value);
								    	//console.log($('#registaId').val())
								        return false;
								    }
								});
							</script>
							<!-- end card-body -->			   
				    	</div>
				
					</div>	
	      		</div>
			  
			</main>
			
			<!-- Footer -->
			<jsp:include page="../footer.jsp" />
	  </body>
</html>