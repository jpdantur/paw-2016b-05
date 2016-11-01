$ document .ready !->

	$ '#myTab a, #itemsTab a, #salesTab a, #purchaseTab a' .click (e) !->
		e.preventDefault!

		$ this .tab \show

	$ 'ul.nav-tabs > li > a' .on 'shown.bs.tab', (e) !->
		id = $ e.target .attr \href .substr(1)
		window.location.hash = id

	$ 'form' .submit (e) !->

		action = $ this .attr \action

		$ this .attr \action, action + window.location.hash

	onHashChange = (hash) !->

		parts = hash.split \-
		mainHash = parts[0]

		$ "\#myTab a[href='#mainHash']" .tab \show

		if parts.length > 1
			$ "\#itemsTab a[href='#hash']" .tab \show
			$ "\#salesTab a[href='#hash']" .tab \show
			$ "\#purchaseTab a[href='#hash']" .tab \show

		

	onHashChange window.location.hash

	$ window .on \hashchange, !->
		onHashChange window.location.hash

	$ \.decide-transaction .click (e) !->

		e.preventDefault!

		$self = $ this
		$row = $self .closest \tr

		isApproving = $self.hasClass \btn-success

		bootbox.confirm "Esta seguro que desea #{if isApproving then 'aprobar' else 'rechazar' } esta venta", (r) !->
			if r
				$ .ajax do
					url: "#{baseUrl}/store/sales/#{$row.data('id')}/#{if isApproving then 'approve' else 'decline'}"
					type: 'POST'
					success: (data) !->
						if data.err
							$.notify {
								message: 'Mensaje de error que le falta i18n'
							} , do
								type: 'danger'
						else
							$.notify {
								message: 'Mensaje de exito que le falta i18n'
							} , do
								type: 'success'

							if isApproving
								$self .next! .remove!
								$self .text 'Venta Aprobada'
							else
								$self .prev! .remove!
								$self .text 'Venta Rechazada'

							$self.removeClass \decide-transaction






	$ \.toggle-item-state .click (e) !->
		e.preventDefault!

		$self = $ this
		$row = $self .closest \tr

		isActive = $self .hasClass \btn-default

		bootbox.confirm "Esta seguro que desea #{if isActive then 'pausar' else 'reanudar' } esta publicación", (r) !->
			if r
				$ .ajax do
					url: "#{baseUrl}/store/item/#{$row.data('id')}/#{if isActive then 'pause' else 'resume'}"
					type: 'POST'
					success: (data) !->
						if data.err
							$.notify {
								message: 'Mensaje de error que le falta i18n'
							} , do
								type: 'danger'
						else
							$.notify {
								message: 'Mensaje de exito que le falta i18n'
							} , do
								type: 'success'
							$self .toggleClass 'btn-success btn-default'

							$self .text if isActive then 'Reanudar Publicación' else 'Pausar Publicación'



