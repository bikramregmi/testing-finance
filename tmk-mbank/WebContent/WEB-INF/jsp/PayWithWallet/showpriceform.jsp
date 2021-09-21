<br/>
<div class="well form-horizontal" >

	<h4>{{p2pmessage}}</h4>
	
	<div class="form-group">
		<label class="col-md-4 control-label">Store ID </label>
		<div class="col-md-4 inputGroupContainer">
			<div class="input-group">
				<span class="input-group-addon"><i
					class="glyphicon glyphicon-user"></i></span> <input ng-model="storeid"
					class="form-control" type="text">
			</div>
		</div>
	</div>
	
	<div class="form-group">
		<label class="col-md-4 control-label">Book Name </label>
		<div class="col-md-4 inputGroupContainer">
			<div class="input-group">
				<span class="input-group-addon"><i
					class="glyphicon glyphicon-user"></i></span> <input ng-model="bookname"
					class="form-control" type="text">
			</div>
		</div>
	</div>

	<div class="form-group">
		<label class="col-md-4 control-label">Amount: </label>
		<div class="col-md-4 inputGroupContainer">
			<div class="input-group">
				<span class="input-group-addon"><i
					class="glyphicon glyphicon-user"></i></span> <input ng-model="bookamount"
					class="form-control" type="text">
					
			</div>
		</div>
	</div>

	<div class="form-group">
		<label class="col-md-4 control-label"></label>
		<div class="col-md-4">
			<button type="submit" class="btn btn-warning" ng-click="peerTransfer()">
				Pay with wallet <span class="glyphicon glyphicon-send"></span>
			</button>
		</div>
	</div>
</div>
