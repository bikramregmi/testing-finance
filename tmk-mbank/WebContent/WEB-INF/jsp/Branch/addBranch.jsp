<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->


<spr:page header1="Add Branch">
<input type="hidden" ng-model="uploadUrl"
		ng-init="uploadUrl='${pageContext.request.contextPath}/bulkBranchUpload'" />
<button class="btn btn-primary"style="float:right;" data-toggle="modal" data-target="#bankBranchUpload">Bulk Upload</button>
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="${pageContext.request.contextPath}/bank/branch/add" modelAttribute="branch" method="post"
				class="col-md-12"
				style="float: none; margin-left: auto; margin-right: auto;">
				<div class="form-group">
					<label>Branch Name</label> <input type="text" name="name"
						class="form-control input-sm" required="required"
						value="${branch.name}">
					<p class="error"><font color="red">${error.name}</font></p>
				</div>
				<div class="form-group">
					<label>Branch Log ID</label> <input type="text" name="branchId"
						class="form-control input-sm" required="required"
						value="${branch.branchId}">
					<p class="error"><font color="red">${error.branchId}</font></p>
				</div>
				<div class="form-group">
					<label>Branch Code</label> <input type="text" name="branchCode"
						class="form-control input-sm" required="required"
						value="${branch.branchCode}">
					<p class="error"><font color="red">${error.branchCode}</font></p>
				</div>
				<div class="form-group">
					<label>Branch Address</label> <input type="text" name="address"
						class="form-control input-sm" required="required"
						value="${branch.address}">
					<p class="error"><font color="red">${error.address}</font></p>
				</div>
				
				<div class="form-group">			
					<label>State</label> 
					<select	name="state" class="form-control input-sm" required="required" id="state">
						<c:if test="${fn:length(statesList) gt 0}">
							<option>Select State</option>
							<c:forEach var="state" items="${statesList}">
								<option value="${state.name}">${state.name}</option>
							</c:forEach>
						</c:if>
					</select> 
					<p class="error"><font color="red">${error.state}</font></p>
				</div>
				<div class="form-group">
					<label>City</label>
					<select name="city" class="form-control input-sm" required="required" id="city">
						
					</select>
						<p class="error"><font color="red">${error.city}</font></p>
				</div>
				
					<div class="form-group">
					<label>Mobile Number</label>
					<input type="text" name="mobileNumber" id="mobileNumber" class="form-control input-sm" value="${branch.mobileNumber}">
					<p class="error"><font color="red">${error.mobileNo}</font></p>			
				</div>
				
				<div class="form-group">
					<label>Email Id</label>
					<input type="email" name="email" id="email" class="form-control input-sm" required="required" value="${branch.email}">
				<p class="error"><font color="red">${error.email}</font></p>	
				</div>
				
				<div class="form-group">
					<label>Latitude</label>
					<input type="text" name="latitude" id="latitude" class="form-control input-sm" value="${branch.latitude}">
				<p class="error"><font color="red">${error.latitude}</font></p>	
				</div>
					<div class="form-group">
					<label>Longitude</label>
					<input type="text" name="longitude" id="longitude" class="form-control input-sm" value="${branch.longitude}">
				<p class="error"><font color="red">${error.longitude}</font></p>	
				</div>
				
				<div class="form-group">
					<table>
						<tr><td><label>Checker</label></td><td>&nbsp&nbsp&nbsp<input type="checkbox" name="checker" id="checker" /></td>
						<td>&nbsp&nbsp&nbsp<label> Maker </label></td><td >&nbsp&nbsp&nbsp<input type="checkbox" name="maker" id="maker" /></td></tr>
						
					</table>
				</div>
				
				<!-- <input id="map-input" type="text" placeholder="Search">
				<div class="form-group" id="map" style="width: 700px; height: 500px;"></div>
				<div>
					<input type="hidden" id="latitude" name="latitude" />
					<input type="hidden" id="longitude" name="longitude" />
				</div> -->
				
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Add
						Branch</button>
				</div>
			</form>

		</div>
		
			<!-- Modal -->
	<div id=bankBranchUpload class="modal fade" role="dialog" tabindex="-1">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Bulk Upload</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
				<div id="page-wrapper">
		<div class="container-fluid">

			<form method="post" enctype="multipart/form-data">

				<div class="file-upload">
					<div class="file-select">
						<div class="file-select-button" id="fileName">Choose File</div>
						<div class="file-select-name" id="noFile">No file chosen...</div>
						<input type="file" name="file" file-model="fileModel"
							id="chooseFile">
					</div>
					<br /> <input type="submit" ng-click="uploadBulkFile(uploadUrl)"
						class="btn  btn-default" style="margin-left: 0px;" value="upload">
				</div>
			</form>

		</div>
	</div>
				</div>

			</div>
		</div>
	</div>
	</div>
</spr:page>
<c:if test="${branch.checker eq 'true'}">
<script>
$("#checker").prop("checked", true);
</script>
</c:if>
<c:if test="${branch.maker eq 'true'}">
<script>
$("#maker").prop("checked", true);
</script>
</c:if>


<script>
	$(document).ready(function(){
		$("#state").change(function(){
			var stateName = $("#state").find("option:selected").val();
			$("#city").find("option").remove();
			var option = '';
			var option = '<option value="'+0+'">Select City</option>'
			$.ajax({
				type: "GET",
				url: "/ajax/state/getCitiesByState?state="+stateName,
				success: function(data) {
					$.each(data.citiesList,function(index,city){			
						option+= '<option value="'+city.id + '">' +city.name+ '</option>';
						$("#city").append(option);
						option = '';
					});
				}
			});
		});
	});
</script>

<!-- <script>
	var label = 'A';
	var labelIndex = 0;
	var marker = null
	function initialize() {
	    var kathmandu = {
	        lat: 27.7172,
	        lng: 85.3240
	    };
	    var map = new google.maps.Map(document.getElementById('map'), {
	        zoom: 12,
	        center: kathmandu
	    });

	    /* for changing the location from name of city */
	    var geocoder = new google.maps.Geocoder();

	    document.getElementById('city').addEventListener('change', function() {
	        geocodeAddress(geocoder, map);
	    });

	    function geocodeAddress(geocoder, resultsMap) {
	        var address = document.getElementById('city').value;

	        console.log(address);
	        geocoder
	            .geocode({
	                    'address': address
	                },
	                function(results, status) {
	                    if (status === 'OK') {
	                        resultsMap
	                            .setCenter(results[0].geometry.location);
	                        var marker = new google.maps.Marker({
	                            map: resultsMap,
	                            position: results[0].geometry.location
	                        });
	                    } else {
	                        alert('Geocode was not successful for the following reason: ' +
	                            status);
	                    }
	                });
	    }

	    /* compelted */

	    google.maps.event.addListener(map, 'click', function(event) {
	        marker = addMarker(event.latLng, map, marker);
	        var latlng = new google.maps.LatLng(event.latLng.lat(),
	            event.latLng.lng());
	        var geocoder = geocoder = new google.maps.Geocoder();
	        geocoder.geocode({
	            'latLng': latlng
	        }, function(results, status) {
	            if (status == google.maps.GeocoderStatus.OK) {

	                if (results[1]) {
	                    $('#longitude').val(event.latLng.lng());
	                    $('#latitude').val(event.latLng.lat());
	                    $('#address').val(results[1].formatted_address);

	                }
	            }
	        });

	    });
	    marker = addMarker(kathmandu, map, marker);

	    var input = document.getElementById('map-input');
	    var searchBox = new google.maps.places.SearchBox(input);
	    map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

	    map.addListener('bounds_changed', function() {
	        searchBox.setBounds(map.getBounds());
	    });

	    searchBox.addListener('places_changed', function() {
	        var places = searchBox.getPlaces();

	        if (places.length == 0) {
	            return;
	        }
	        var bounds = new google.maps.LatLngBounds();
	        places.forEach(function(place) {
	            if (!place.geometry) {
	                console.log("Returned place contains no geometry");
	                return;
	            }
	            if (place.geometry.viewport) {
	                bounds.union(place.geometry.viewport);
	            } else {
	                bounds.extend(place.geometry.location);
	            }
	        });
	        map.fitBounds(bounds);
	    });
	}

	function addMarker(location, map, marker) {
	    if (marker == null) {
	        marker = new google.maps.Marker({
	            position: location,
	            label: label,
	            map: map
	        });
	        console.log(marker.position);
	        return marker;
	    } else {
	        marker.setMap(null);
	        marker = new google.maps.Marker({
	            position: location,
	            label: label,
	            map: map
	        });
	        return marker;
	    }
	}

	$("#map-input").keypress(function(e) {
	    if (e.key == "Enter") {
	        e.preventDefault();
	    }
	});
</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCtNDzgNspNhLoyrAVU6F_RMGKnFUzj85o&libraries=places&callback=initialize">
</script> -->
