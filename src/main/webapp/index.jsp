<html>
<body>

	<!-- Put all references to external libraries here -->
	<script
		src="//cdnjs.cloudflare.com/ajax/libs/require.js/2.1.8/require.min.js"></script>
	<!-- CSS for Bootstrap -->
	<link rel="stylesheet" type="text/css"
		href="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.2/css/bootstrap.min.css"></link>

	<!-- Main login page -->
	<!-- UI Elements -->
	<div class="" id="loginModal">
		<div class="span5">
			<div class="modal-header">
				<h3>Login</h3>
			</div>
			<div class="modal-body">
				<div class="well">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#student" data-toggle="tab">Student</a></li>
						<li><a href="#admin" data-toggle="tab">Admin</a></li>
					</ul>
					<div class="alert"  id="adminAlert">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						<strong>Warning!</strong> Username or Password was incorrect
					</div>

					<div class="alert" id="studentAlert">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						<strong>Warning!</strong> Email Address not Valid
					</div>
					<div id="myTabContent" class="tab-content">
						<div class="tab-pane active in" id="student">
							<div class="control-group">
								<!-- Email of Student -->
								<label class="control-label">Email</label>
								<div class="controls">
									<input type="text" id="studentInput"
										placeholder="billybob@example.com" class="input-large">
								</div>
							</div>
							<div class="control-group">
								<!-- Button -->
								<div class="controls">
									<button class="btn btn-success" id="studentLogin">Login</button>
								</div>
							</div>
						</div>

						<div class="tab-pane fade" id="admin">
							<label class="control-label">Username</label> <input type="text"
								class="input-large" id="adminUsername"> <label
								class="control-label">Password</label> <input type="password"
								class="input-large" id="adminPassword">

							<div>
								<button class="btn btn-success" id="adminLogin">Login</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- My stuff. HAS TO GO LAST -->
	<script type="text/javascript" src="app.js"></script>
</body>
</html>
