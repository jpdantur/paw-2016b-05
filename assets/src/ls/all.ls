$ document .ready !->

	$ \.remove-fav .click (e) !->

		e.preventDefault!

		$self = $ this

		item = $self .data \id

		$ .ajax do
			url: baseUrl + '/favourites/remove/' + item
			type: 'POST'
			success: (data) !->
				console.log data
				if data.err
					$.notify {
						message: globalMessages.removeError
					} , do
						type: 'danger'
				else
					$.notify {
						message: globalMessages.removeSuccess
					} , do
						type: 'success'
					$self .closest 'li' .remove!
					