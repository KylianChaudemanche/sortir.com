<!-- ####### HEADER ######## -->
<%@include file="includes/header.jsp"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!-- ####### NAVBAR ######## -->
<%@include file="includes/navbar.jsp"%>


<!-- ####### CONTENT ######## -->

<c:if test = "${error != null}">
    <div class="alert alert-danger" role="alert">
  	<c:out value = "${error}"/>
	</div>
</c:if>

<div class="container">
	<h1 class="text-center mb-4 mt-4">Modifier une sortie</h1>
	
	<form action="updateSortie" method="POST">
		<input type="hidden" name="sortieNoSortie" value="${sortie.noSortie}">
		<div class="row">
			<div class="col-sm-6 float-left">
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Nom de la sortie</label>
					<div class="col-sm-8">
						<input class="form-control" type="text" name="sortieName" value="${sortie.nom}" required>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Date et heure de la sortie</label>
					<div class="col-sm-8">
						<input class="form-control" type="datetime-local" name="sortieBeginDate" value="<fmt:formatDate value='${sortie.dateDebut}' pattern='yyyy-MM-dd\'T\'hh:mm' />" required>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Date limite d'inscription</label>
					<div class="col-sm-8">
						<input class="form-control" type="date" name="sortieCloseInscriptionDate" value="<fmt:formatDate value='${sortie.dateCloture}' pattern='yyyy-MM-dd' />"required>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Nombre de place</label>
					<div class="col-sm-8">
						<input class="form-control" type="number" name="sortieNbMaxPlace" value="${sortie.nbInscriptionsMax}" required>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Dur�e</label>
					<div class="col-sm-8">
						<input class="form-control col-sm-3 d-inline" name="sortieDuration" value="${sortie.duree}" type="number" min="1" max="999" step="1" required>
						<p class="d-inline ml-3">minutes</p>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Description et infos</label>
					<div class="col-sm-8">
						<textarea class="form-control" name="sortieDesc" rows="3" required>${sortie.descriptionInfos}</textarea>
					</div>
				</div>
			</div>
			
			<div class="col-sm-6 float-right">
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Ville organisatrice</label>
					<div class="col-sm-8">
						<input type='text'
						       class='form-control'
						       data-min-length='3'
						       data-search-in='name'
						       data-selection-required='true'
						       data-value-property='id'
						       id="sortieCity"
						       name='sortieCity'
						       value="${sortie.lieu.ville.nomVille}">
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Lieu</label>
					<div class="col-sm-8">
						<input type='text'
					       class='form-control'
					       data-min-length='2'
					       data-search-in='name'
					       data-selection-required='true'
					       data-value-property='id'
					       id="sortiePlace"
					       name='sortiePlace'
					       value="${sortie.lieu.nomLieu}">
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Adresse</label>
					<div class="col-sm-8">
						<input id="sortieAddress" class="form-control" type="text" value="${sortie.lieu.adresse}" disabled>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Code postal</label>
					<div class="col-sm-8">
						<input id="sortieCodePostal" class="form-control" type="text" value="${sortie.lieu.ville.codePostal}" disabled>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Latitude</label>
					<div class="col-sm-8">
						<input id="sortieLatitude" class="form-control"type="text" value="${sortie.lieu.latitude}" disabled>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Longitude</label>
					<div class="col-sm-8">
						<input id="sortieLongitude" class="form-control"type="text" value="${sortie.lieu.longitude}" disabled>
					</div>
				</div>
			</div>
		</div>
		<div class="form-group row">
			<div class="mx-auto mt-2">
		      	<button type="submit" name="action" class="btn btn-secondary btn-align" value="save">Enregistrer</button>
		      	<button type="submit" name="action" class="btn btn-secondary btn-align" value="publish">Publier la sortie</button>
		      	<button type="submit" name="action" class="btn btn-secondary btn-align" value="delete">Supprimer la sortie</button>
		      	<a href="#"><button class="btn btn-secondary btn-align">Annuler</button></a>
	      	</div>
   		</div>
	</form>
</div>

<!-- ####### </>CONTENT ######## -->

<!-- ####### FOOTER ######## -->
<%@include file="includes/footer.jsp"%>

<script>
$(function() {
	var cities = [
		<c:forEach items="${cities}" var="city">
			{
				name: "${city.nomVille}",
				id: "${city.noVille}",
				codePostal: "${city.codePostal}"
			},
		</c:forEach> 
	];
	
	var places = [
		<c:forEach items="${places}" var="place">
			{
				name: "${place.nomLieu}",
				id: "${place.noLieu}",
				address: "${place.adresse}",
				latitude: "${place.latitude}",
				longitude: "${place.longitude}",
				cityId: "${place.ville.noVille}"
			},
		</c:forEach> 
	];
	
	$('#sortieCity').flexdatalist({
	     minLength: 3,
	     searchIn: 'name',
	     data: cities,
	     valueProperty: 'id',
	     selectionRequired: true
	});
	
	var place = places.filter(place => place.id == "${sortie.lieu.noLieu}")
	
	$('#sortiePlace').flexdatalist({
	     minLength: 2,
	     searchIn: 'name',
	     data: place,
	     valueProperty: 'id',
	     selectionRequired: true
	});
	
	$('#sortieCity').val("${sortie.lieu.ville.noVille}");
	$('#sortiePlace').val("${sortie.lieu.noLieu}");
	
	$('#sortieCity').on("change", function() {
		var cityId = $('#sortieCity').val();
		var city = cities.filter(city => city.id == cityId)[0];
		if (city) {
			$("#sortieCodePostal").val(city.codePostal);
			var placeFiltred = places.filter(place => place.cityId == cityId)
			$('#sortiePlace').flexdatalist({
			     minLength: 2,
			     searchIn: 'name',
			     data: placeFiltred,
			     valueProperty: 'id',
			     selectionRequired: true
			});
			$('#sortiePlace').on("change", function() {
				var placeId = $('#sortiePlace').val();
				var place = places.filter(place => place.id == placeId)[0];
				$("#sortieAddress").val(place.address);
				$("#sortieLongitude").val(place.longitude);
				$("#sortieLatitude").val(place.latitude);
			});
		}
	});
});
</script>