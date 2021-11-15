<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<header>
  <!-- Fixed navbar -->
 <nav class="navbar navbar-expand-lg navbar-dark bg-primary" aria-label="Eighth navbar example">
    <div class="container">
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsExample07" aria-controls="navbarsExample07" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarsExample07">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/home">Home</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Link</a>
          </li>
          <li class="nav-item">
            <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
          </li>
           <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="dropdown07" data-bs-toggle="dropdown" aria-expanded="false">Tavoli</a>
            <ul class="dropdown-menu" aria-labelledby="dropdown07">
				<li><a class="dropdown-item" href="${pageContext.request.contextPath}/tavolo/search">Ricerca tavolo</a></li>
				 <sec:authorize access="hasRole('ADMIN') || hasRole('SPECIAL_PLAYER')">
          		<li><a class="dropdown-item" href="${pageContext.request.contextPath}/tavolo/insert">Crea tavolo</a></li>
          		<li><a class="dropdown-item" href="${pageContext.request.contextPath}/tavolo/findMyTables">I miei tavoli</a></li>
          		<li><a class="dropdown-item" href="${pageContext.request.contextPath}/tavolo/gestione">Gestisci tavoli</a></li>
          		   </sec:authorize>
            </ul> 
          </li>
           <sec:authorize access="hasRole('ADMIN')">
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Gestione Utenze</a>
		        <div class="dropdown-menu" aria-labelledby="dropdown01">
		          <a class="dropdown-item" href="${pageContext.request.contextPath}/admin/search">Ricerca Utenti</a>
		          <a class="dropdown-item" href="${pageContext.request.contextPath}/admin/insert">Inserisci Utente</a>
		        </div>
		      </li>
		   </sec:authorize>
        </ul>
      </div>
  
    	 <sec:authorize access="isAuthenticated()">
			<div class="nav-item dropdown">
				<a class="nav-link active dropdown-toggle" style="color:white!important" href="#" id="dropdown01" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				 Utente: <sec:authentication property="name"/> (${userInfo.nome } ${userInfo.cognome })
					</a>
			    <div class="dropdown-menu" aria-labelledby="dropdown01">
			    <a class="dropdown-item" href="${pageContext.request.contextPath}/user/resetUserPassword">Reset Password</a>
			      <a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Logout</a>
			    </div>
		    </div>
		   </sec:authorize>
    
    </div>
  </nav>
  
  
</header>
