$ document .ready !->

	$ \.toggle-default .click (e) !->

		e.preventDefault!

		$self = $ this
		$row = $self .closest \tr

		if $self .hasClass \text-success
			return

		$ .ajax do
			url: baseUrl + '/admin/roles/default/'+$row.data \id
			type: 'POST'
			success: (data) !->

				if data.err
					bootbox.alert messages.fail
				else
					$row .closest(\table) .find(\.default-role) .find(\.text-success) .toggleClass('text-success text-muted') .find('i') .toggleClass 'fa-check fa-minus'
					$self .toggleClass('text-success text-muted') .find('i') .toggleClass 'fa-check fa-minus'