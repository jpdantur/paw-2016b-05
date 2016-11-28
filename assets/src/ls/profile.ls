$ document .ready !->

	$ '[data-toggle="tooltip"]' .tooltip!

	$ \#score .rate do
		max_value: 5,
		step_size: 0.5,
		update_input_field_name: $('#score-input')

	$ '#itemsTab a, #salesTab a, #purchaseTab a' .click (e) ->
		# e.preventDefault!

		# console .log \aaa

		# $ this .tab \show

		# return false

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

	reviewBuyer = ($self, r) !->
		if r
			$ \#review-modal .modal(\show) .data \target, $self


	$ \.show-scores .click (e) !->

		e.preventDefault!

		$self = $ this

		buyer-rating = $self.data \b-rating
		seller-rating = $self.data \s-rating

		$modal = $ \#review-modal-readonly

		$modal .find \.rating .rate do
			readonly: true
		.rate \setValue, seller-rating.rating

		$modal .find \#content-show .text seller-rating.comment

		$modal .modal \show


	$ \#rate-action .click (e) !->

		e.preventDefault!

		$self = $ this

		$content = $ \#content
		$target = $ \#review-modal .data \target
		$row = $target .closest \tr


		if !$content.val!

			$content .closest \.form-group .addClass \has-error
			$content .after '<span class="help-block">Contenido vacio</span>'

			return

		$self .addClass \disabled

		console.log( $ \#comment-form .serialize!)

		$ .ajax do
			url: "#{baseUrl}/store/sales/#{$row.data('id')}/seller/review"
			data: $ \#comment-form .serialize!
			type: 'POST'
			success: (data) !->
				$self .removeClass \disabled
				if data.err
					$.notify {
						message: 'Error'
					} , do
						type: 'danger'
						z_index: 1300
				else
					$.notify {
						message: 'Exito'
					} , do
						type: 'success'
						z_index: 1300

	$ \.decide-transaction .click (e) !->

		e.preventDefault!

		$self = $ this
		if $self .hasClass \disabled
			return
		$row = $self .closest \tr

		isApproving = $self.hasClass \btn-success

		$self .addClass \disabled

		actionDesc = if isApproving then messages.sellApprove else messages.sellReject
		bootbox.confirm messages.sellConfirmation + actionDesc + messages.sellConfirmation2, (r) !->
			if r
				$ .ajax do
					url: "#{baseUrl}/store/sales/#{$row.data('id')}/#{if isApproving then 'approve' else 'decline'}"
					type: 'POST'
					success: (data) !->
						$self .removeClass \disabled
						if data.err
							$.notify {
								message: messages.sellError + ( if isApproving then messages.approving else messages.rejecting )
							} , do
								type: 'danger'
								z_index: 1300
						else
							$.notify {
								message: messages.sellSuccess
							} , do
								type: 'success'
								z_index: 1300

							if isApproving
								$self .next! .remove!
								$self .text messages.sellApproved
							else
								$self .prev! .remove!
								$self .text messages.sellRejected

							bootbox.confirm "Desea calificar al comprador ahora?", (r) !->
								reviewBuyer($self, r)

			else
				$self .removeClass \disabled
							# $self .prop(\disabled, \disabled) .removeClass \decide-transaction

	$ document.body .on \click, \.toggle-item-state, (e) !->
		e.preventDefault!

		$self = $ this
		if $self .hasClass \disabled
			return
		$row = $self .closest \tr

		isActive = $self .hasClass \btn-default

		$self .addClass \disabled

		actionDesc = if isActive then messages.sellPause else messages.sellResume
		bootbox.confirm messages.sellConfirmation + actionDesc + messages.sellConfirmation2, (r) !->
			if r
				$ .ajax do
					url: "#{baseUrl}/store/item/#{$row.data('id')}/#{if isActive then 'pause' else 'resume'}"
					type: 'POST'
					success: (data) !->
						$self .removeClass \disabled
						if data.err
							$.notify {
								message: messages.sellError + ( if isActive then messages.pausing else messages.resuming )
							} , do
								type: 'danger'
						else
							$.notify {
								message: messages.sellSuccess
							} , do
								type: 'success'
							$self .toggleClass 'btn-success btn-default'

							$self .text if isActive then messages.sellResumeBtn else messages.sellPauseBtn
			else
				$self .removeClass \disabled
							
	$ document.body .on \click, \.publish, (e) !->
		e.preventDefault!

		$self = $ this
		if $self .hasClass \disabled
			return
		$row = $self .closest \tr

		$self .addClass \disabled

		isActive = $self .hasClass \btn-default

		bootbox.confirm messages.sellPublishConfirm, (r) !->
			if r
				$ .ajax do
					url: "#{baseUrl}/store/item/#{$row.data('id')}/publish"
					type: 'POST'
					success: (data) !->
						$self .removeClass \disabled
						if data.err
							$.notify {
								message: messages.sellError + messages.publishing
							} , do
								type: 'danger'
						else
							$.notify {
								message: messages.sellSuccess
							} , do
								type: 'success'
							$self .toggleClass 'publish btn-success btn-default toggle-item-state' .text messages.sellPauseBtn
			else
				$self .removeClass \disabled
	


	query = {}

	buildQuery = !->

		query.pageNumber = parseInt( $ \#filter-page .data \page )
		query.pageSize = parseInt($ \#modifier-page-size .find \.modifier-option.selected .text!)
		query.query = $ \#query-input .val!

		$filterOption = $ \#modifier-order-input .find \option:selected

		filterOption = $filterOption .val! .split '-'

		query.orderBy = filterOption[0].toUpperCase!
		query.sortOrder = filterOption[1].toUpperCase!

	submit = !->

		console.log query

		window.location.search = $ .param query, true

	resetPageNumber = !->

		query.pageNumber = 0

	if $ \#query-input .length
		buildQuery!

	$ \#query-input .keyup (e) !->
		if e.keyCode == 13
			e.preventDefault!

			buildQuery!
			resetPageNumber!
			submit!

	$ \.filter-page-action .click (e) !->
		e.preventDefault!

		$self = $ this

		if $self .parent! .hasClass \disabled
			return

		if $self .parent! .hasClass \previous
			query.pageNumber = 0

		if $self .parent! .hasClass \pre
			query.pageNumber -= 1;

		if $self .parent! .hasClass \ne
			query.pageNumber += 1;

		if $self .parent! .hasClass \next
			query.pageNumber = parseInt($ \#filter-page .data('last-page'))

		submit!

	$ \#modifier-order-input .change (e) !->
		e.preventDefault!

		buildQuery!
		resetPageNumber!
		submit!

	$ \.modifier-option .click (e) !->
		e.preventDefault!

		$self = $ this
		$row = $self .parent!

		if $self .hasClass \selected
			return

		$row .find \.selected .toggleClass \selected
		$self .toggleClass \selected

		buildQuery!
		resetPageNumber!
		submit!