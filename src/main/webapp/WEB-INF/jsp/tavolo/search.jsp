<!doctype html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
		<!-- Custom styles per le features di bootstrap 'Columns with icons' -->
	   <link href="${pageContext.request.contextPath}/assets/css/features.css" rel="stylesheet">
	   <link href="assets/css/home.css" rel="stylesheet">
	   <link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/jqueryUI/jquery-ui.min.css" />
		<style>
			.ui-autocomplete-loading {
				background: white url("../assets/img/jqueryUI/anim_16x16.gif") right center no-repeat;
			}
			.error_field {
		        color: red; 
		    }
		</style>
	   
	   <title>PokerOnline</title>
	 </head>
	   <body class="d-flex flex-column h-100">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
			
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
				        
				        <form method="post" action="list" class="row g-3">
								
							<div class="col-md-6">
								<label for="denominazione" class="form-label">Denominazione</label>
								<input type="text" name="denominazione" id="denominazione" class="form-control" placeholder="Inserire denominazione" value="${search_tavolo_attr.denominazione }">
							</div>
							
							<div class="col-md-6">	
								<fmt:formatDate pattern='yyyy-MM-dd' var="parsedDate" type='date' value='${search_tavolo_attr.dataCreazione}' />
								<div class="form-group col-md-6">
									<label for="dataCreazione" class="form-label">Data di Creazione</label>
		                        	<input class="form-control " id="dataCreazione" type="date" placeholder="dd/MM/yy"
		                            		title="formato : gg/mm/aaaa"  name="dataCreazione" value="${parsedDate}" >
								</div>
							</div>
								
							<div class="col-md-6">
								<label for="cifraMinima" class="form-label">Puntata minima</label>
								<input type="text" name="cifraMinima" id="cifraMinima" class="form-control " placeholder="Inserire puntata minima" value="${search_tavolo_attr.cifraMinima }">
							</div>
							
							<div class="col-md-6">
								<label for="utenteCreatoreSearchInput" class="form-label">Creatore tavolo:</label>
								<input class="form-control " type="text" id="utenteCreatoreSearchInput"
										name="utenteCreatoreInput" value="${search_tavolo_attr.utenteCreatore.nome}${search_tavolo_attr.utenteCreatore.cognome}">
								<input type="hidden" name="utenteCreatore.id" id="utenteCreatoreId" value="${search_tavolo_attr.utenteCreatore.id}">
							</div>
							
							<div class="col-md-6">
								<label for="giocatoriSearchInput" class="form-label">Giocatori:</label>
								<input class="form-control " type="text" id="giocatoriSearchInput"
										name="giocatoriInput" value="${search_tavolo_attr.giocatoreCercato.nome}">
								<input type="hidden" name="giocatori.id" id="giocatoriId" value="${search_tavolo_attr.giocatoreCercato.id}">
							</div>
	
							<div class="col-12">	
								<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
							</div>
										
						</form>
						
						<script>
							$("#utenteCreatoreSearchInput").autocomplete({
								 source: function(request, response) {
								        $.ajax({
								            url: "../tavolo/searchUtentiAjax",
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
							        $("#utenteCreatoreSearchInput").val(ui.item.label)
							        return false
							    },
							    minLength: 2,
							    //quando seleziono la voce nel campo hidden deve valorizzarsi l'id
							    select: function( event, ui ) {
							    	$('#utenteCreatoreSearchInputId').val(ui.item.value);
							    	//console.log($('#registaId').val())
							        return false;
							    }
							});
						</script>
						<script>
							$("#giocatoriSearchInput").autocomplete({
								 source: function(request, response) {
								        $.ajax({
								            url: "../tavolo/searchUtentiAjax",
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
							        $("#giocatoriSearchInput").val(ui.item.label)
							        return false
							    },
							    minLength: 2,
							    //quando seleziono la voce nel campo hidden deve valorizzarsi l'id
							    select: function( event, ui ) {
							    	$('#giocatoriSearchInput').val(ui.item.value);
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