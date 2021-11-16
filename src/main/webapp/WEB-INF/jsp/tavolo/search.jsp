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
	   		
	   		<!-- #####################################  -->
	   		<!-- elementi grafici per le features in basso  -->
	   		<!-- #####################################  -->
	   		<svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
			  <symbol id="people-circle" viewBox="0 0 16 16">
			    <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
			    <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
			  </symbol>
			  <symbol id="collection" viewBox="0 0 16 16">
			    <path d="M2.5 3.5a.5.5 0 0 1 0-1h11a.5.5 0 0 1 0 1h-11zm2-2a.5.5 0 0 1 0-1h7a.5.5 0 0 1 0 1h-7zM0 13a1.5 1.5 0 0 0 1.5 1.5h13A1.5 1.5 0 0 0 16 13V6a1.5 1.5 0 0 0-1.5-1.5h-13A1.5 1.5 0 0 0 0 6v7zm1.5.5A.5.5 0 0 1 1 13V6a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-.5.5h-13z"/>
			  </symbol>
			  <symbol id="toggles2" viewBox="0 0 16 16">
			    <path d="M9.465 10H12a2 2 0 1 1 0 4H9.465c.34-.588.535-1.271.535-2 0-.729-.195-1.412-.535-2z"/>
			    <path d="M6 15a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm0 1a4 4 0 1 1 0-8 4 4 0 0 1 0 8zm.535-10a3.975 3.975 0 0 1-.409-1H4a1 1 0 0 1 0-2h2.126c.091-.355.23-.69.41-1H4a2 2 0 1 0 0 4h2.535z"/>
			    <path d="M14 4a4 4 0 1 1-8 0 4 4 0 0 1 8 0z"/>
			  </symbol>
			  <symbol id="chevron-right" viewBox="0 0 16 16">
			    <path fill-rule="evenodd" d="M4.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L10.293 8 4.646 2.354a.5.5 0 0 1 0-.708z"/>
			  </symbol>
			</svg>
			<!-- ############## end ###################  -->
	   
	   
	   
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
									<label for="utenteGiocatoreSearchInput" class="form-label">Giocatori:</label>
									<input class="form-control " type="text" id="utenteGiocatoreSearchInput"
											name="utenteGiocatoreSearchInput" >
									<input type="hidden" name="tavolo.id" id="utenteGiocatoreSearchInputId" >
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
								$("#utenteGiocatoreSearchInput").autocomplete({
									 source: function(request, response) {
									        $.ajax({
									            url: "${pageContext.request.contextPath }/utente/searchUtentiAjax",
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
								        $("#utenteGiocatoreSearchInput").val(ui.item.label)
								        return false
								    },
								    minLength: 2,
								    //quando seleziono la voce nel campo hidden deve valorizzarsi l'id
								    select: function( event, ui ) {
								    	$('#utenteGiocatoreSearchInputId').val(ui.item.value);
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