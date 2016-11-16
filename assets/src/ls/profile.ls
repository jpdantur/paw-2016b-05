$ document .ready !->

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

	$ \.decide-transaction .click (e) !->

		e.preventDefault!

		$self = $ this
		$row = $self .closest \tr

		isApproving = $self.hasClass \btn-success

		actionDesc = if isApproving then messages.sellApprove else messages.sellReject
		bootbox.confirm messages.sellConfirmation + actionDesc + messages.sellConfirmation2, (r) !->
			if r
				$ .ajax do
					url: "#{baseUrl}/store/sales/#{$row.data('id')}/#{if isApproving then 'approve' else 'decline'}"
					type: 'POST'
					success: (data) !->
						if data.err
							$.notify {
								message: messages.sellError
							} , do
								type: 'danger'
						else
							$.notify {
								message: messages.sellSuccess
							} , do
								type: 'success'

							if isApproving
								$self .next! .remove!
								$self .text messages.sellApproved
							else
								$self .prev! .remove!
								$self .text messages.sellRejected

							$self .prop(\disabled, \disabled) .removeClass \decide-transaction

	$ \.toggle-item-state .click (e) !->
		e.preventDefault!

		$self = $ this
		$row = $self .closest \tr

		isActive = $self .hasClass \btn-default

		actionDesc = if isActive then messages.sellPause else messages.sellResume
		bootbox.confirm messages.sellConfirmation + actionDesc + messages.sellConfirmation2, (r) !->
			if r
				$ .ajax do
					url: "#{baseUrl}/store/item/#{$row.data('id')}/#{if isActive then 'pause' else 'resume'}"
					type: 'POST'
					success: (data) !->
						if data.err
							$.notify {
								message: messages.sellError
							} , do
								type: 'danger'
						else
							$.notify {
								message: messages.sellSuccess
							} , do
								type: 'success'
							$self .toggleClass 'btn-success btn-default'

							$self .text if isActive then messages.sellResumeBtn else messages.sellPauseBtn

	$ \.publish .click (e) !->
		e.preventDefault!

		$self = $ this
		$row = $self .closest \tr

		isActive = $self .hasClass \btn-default

		bootbox.confirm messages.sellPublishConfirm, (r) !->
			if r
				$ .ajax do
					url: "#{baseUrl}/store/item/#{$row.data('id')}/publish"
					type: 'POST'
					success: (data) !->
						if data.err
							$.notify {
								message: messages.sellError
							} , do
								type: 'danger'
						else
							$.notify {
								message: messages.sellSuccess
							} , do
								type: 'success'
							$self .toggleClass 'publish' .prop 'disabled', 'disabled' .text messages.sellPublished

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

	if $ \#query-input .length
		buildQuery!

	$ \#query-input .keyup (e) !->
		if e.keyCode == 13
			e.preventDefault!

			buildQuery!

			query.pageNumber = 0

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
		submit!