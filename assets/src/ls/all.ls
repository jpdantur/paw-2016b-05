$ document .ready !->

	$ \.remove-fav .click (e) !->

		e.preventDefault!

		$self = $ this

		if $self.hasClass \disabled
			return

		item = $self .data \id

		$self .addClass \disabled

		$ .ajax do
			url: baseUrl + '/favourites/remove/' + item
			type: 'POST'
			success: (data) !->
				$self .removeClass \disabled
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
					$self .closest 'li,tr' .remove!
					