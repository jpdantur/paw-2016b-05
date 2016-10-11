$ document .ready !->

	$ \#treegrid .treegrid do
		initialState: 'collapsed'

	$ \.create-subcategory .click (e) !->
		e.preventDefault!

		$self = $ this
		$row = $self .closest 'tr'

		id = $row.data \id
		parent = $row.data \parent

		$iframe = $ '<iframe>', do
			style: 'width:100%;min-height:400px'
			src: "#baseUrl/admin/categories/create?parent=#id&name=-"
			

		$iframe.on 'load', !->
			if $(this).contents().find(\#success).length > 0
				setTimeout !->
					bootbox.hideAll!
					window.location.reload true
				, 1500

		bootbox.dialog do
			message: $iframe
			buttons:
				cancel:
					label: 'Cancelar'
					callback: !->


	$ \.rename-category .click (e) !->

		e.preventDefault!

		$self = $ this
		$row = $self .closest 'tr'

		id = $row.data \id
		parent = $row.data \parent

		$iframe = $ '<iframe>', do
			style: 'width:100%;min-height:400px'
			src: "#baseUrl/admin/categories/#id/edit"
			

		$iframe.on 'load', !->
			if $(this).contents().find(\#success).length > 0
				setTimeout !->
					bootbox.hideAll!
					window.location.reload true
				, 1500

		bootbox.dialog do
			message: $iframe
			buttons:
				cancel:
					label: 'Cancelar'
					callback: !->
