'use strict';
define([], function () {

	return {
    '403.headline': 'Access Denied',
    '403.home': 'Home',
    '404.headline': 'Resource not found',
    '404.home': 'Home',
    '500.headline': 'Ups, we may have done something wrong',
    '500.home': 'Home',
    'Digits.SellForm.price': 'Item cant be free or cost more than a thousand million',
    'Email.ProfileForm.email': 'Email format is invalid',
    'Email.RegisterForm.email': 'Email format is invalid',
    'Email.SellForm.email': 'Email format is invalid',
    'EmailExists.ProfileForm.email': 'Email already exists',
    'EmailExists.RegisterForm.email': 'Email already exists',
    'EmailNotExists.PasswordRecoveryForm.email': 'Email does not exist in our records',
    'Max.SellForm.price': 'Price can\'t be higher than ${1}',
    'Min.SellForm.price': 'Price must be at least ${1}',
    'NotEmpty.CommentForm.content': 'Review can\'t be empty',
    'NotEmpty.ProfileForm.email': 'Email cant be empty',
    'NotEmpty.RegisterForm.email': 'Email cant be empty',
    'NotEmpty.SellForm.email': 'Email is required',
    'NotNull.SellForm.price': 'Price is required',
    'PasswordMatch.ChangePasswordForm.repeatPassword': 'Passwords do not match',
    'PasswordMatch.RegisterForm.repeatPassword': 'Passwords do not match',
    'Pattern.RoleForm.slug': 'Slug name must have uppercase letters, numbers, \'-\' or \'_\' without spaces',
    'Size.CategoryForm.name': 'Name must have between {min} y {max} characters',
    'Size.CommentForm.content': 'Review must have up to {max} characters',
    'Size.CommentForm.contentFixed': 'Review must have up to 300 characters',
    'Size.RegisterForm.firstName': 'Your name must have between {min} y {max} characters',
    'Size.RegisterForm.lastName': 'Your last name must have between {min} y {max} characters',
    'Size.RegisterForm.password': 'Your password must have between {min} y {max} characters',
    'Size.RegisterForm.username': 'Your username must have between {min} y {max} characters',
    'Size.RoleForm.name': 'Role name must have between {min} and {max} characters',
    'Size.RoleForm.slug': 'Slug name must have between {min} and {max} characters',
    'Size.SellForm.name': 'Item name must have between {min} and {max} characters',
    'UsernameExists.RegisterForm.username': 'Username already exists',
    'WrongPassword.ChangePasswordForm.oldPassword': 'This was not your password',
    'admin.categories.create.action': 'Create',
    'admin.categories.create.cancel': 'Cancel',
    'admin.categories.create.headline': 'Create Category',
    'admin.categories.create.name': 'Name',
    'admin.categories.create.success': 'Successful Creation',
    'admin.categories.create.successInfo': 'This window will close automatically',
    'admin.categories.rename.action': 'Rename',
    'admin.categories.rename.headline': 'Renaming category',
    'admin.categories.rename.name': 'Name',
    'admin.categories.rename.success': 'Successful update',
    'admin.categories.rename.successInfo': 'This window will close automatically',
    'admin.category.add': 'New Sub-Category',
    'admin.category.headline': 'Categories',
    'admin.category.rename': 'Rename',
    'admin.index.categories': 'Categories',
    'admin.index.headline': 'Dashboard',
    'admin.index.items': 'Items',
    'admin.index.users': 'Users',
    'admin.roles.add': 'Add',
    'admin.roles.create.headline': 'Create Role',
    'admin.roles.create.name': 'Name',
    'admin.roles.default': 'Default',
    'admin.roles.defaultFail': 'The role could not be made default',
    'admin.roles.defaultSuccess': 'Default role has been changed',
    'admin.roles.headline': 'Roles',
    'admin.roles.isDefault': 'Default role',
    'admin.roles.makeDefault': 'Make Default',
    'admin.roles.name': 'Name',
    'admin.sidebar.roles': 'Roles',
    'admin.users.add': 'Add',
    'admin.users.create.email': 'Email',
    'admin.users.create.headline': 'Create User',
    'admin.users.create.lastName': 'Last Name',
    'admin.users.create.mainRole': 'Main Role',
    'admin.users.create.name': 'First Name',
    'admin.users.create.password': 'Password',
    'admin.users.create.repeatPassword': 'Repeat Password',
    'admin.users.create.username': 'Username',
    'admin.users.edit.add': 'Add',
    'admin.users.edit.addRole': 'New Role',
    'admin.users.edit.headline': 'Roles',
    'admin.users.editRoles': 'Edit Roles',
    'admin.users.email': 'Email',
    'admin.users.headline': 'Users',
    'admin.users.lastName': 'Last Name',
    'admin.users.name': 'First Name',
    'admin.users.roles': 'Roles',
    'admin.users.username': 'Username',
    'auth.forgotpass.success.message': 'Check your mailbox',
    'auth.forgotpass.success.title': 'Email has been sent',
    'edit.action': 'Edit',
    'edit.details': 'Details',
    'edit.details.categoryChosen': 'You chose:',
    'edit.headline': 'Editing',
    'edit.images': 'Images',
    'index.categories': 'Categories',
    'index.favourite.empty': 'You dont have any favourites, add them to see them',
    'index.favourite.remove': 'Remove',
    'index.favourite.title': 'Favourites',
    'index.favourite.viewAll': 'See All',
    'index.headline': 'What do you wish to buy?',
    'index.login': 'Login',
    'index.promoted': 'Best Selling',
    'index.register': 'Register',
    'index.search': 'Search',
    'index.sell': 'Sell',
    'index.user.items': 'My Items',
    'index.user.logout': 'Logout',
    'index.user.profile': 'My Profile',
    'index.user.purchases': 'My Purchases',
    'index.user.sales': 'My Sales',
    'item.bought.underRevision': 'Your purchase is under revision by the seller.',
    'item.bought.viewStatus': 'Click here to see its status',
    'item.buy': 'Buy',
    'item.comment': 'Review',
    'item.commentAction': 'Review',
    'item.commentRequired': 'Review is Required',
    'item.commentLong': 'Review is too long',
    'item.commentShort': 'Review is too short',
    'item.commentEmpty': 'No reviews yet',
    'item.comments': 'Review',
    'item.description': 'Description',
    'item.favourite': 'Mark as favourite',
    'item.item': 'Item Status',
    'item.new': 'New',
    'item.score': 'Score',
    'item.carousel.previous': 'Previous',
    'item.carousel.next': 'Next',
    'item.notifications.draft.message': 'This item is a draft. Click here to edit',
    'item.notifications.draft.title': 'This is a draft',
    'item.notifications.paused.alternative': 'Or click here to edit',
    'item.notifications.paused.message': 'This publication is paused. Click here to resume it',
    'item.notifications.paused.title': 'This publication is paused',
    'item.seller': 'Seller',
    'item.seller.publications': 'Items from the same seller',
    'item.sold': 'sold',
    'item.used': 'Used',
    'login.error.error': 'Error!',
    'login.error.wrongData': 'Wrong username or password',
    'login.field.password': 'Password',
    'login.field.rememberMe': 'Remember me',
    'login.field.user': 'Username',
    'login.form.noUserQuestion': 'Still don\'t have a user?',
    'login.form.register': 'Register',
    'login.recover.help': 'Enter your e-mail and we will send you intructions to recover your password',
    'login.recover.invalidLink': 'The link is not valid',
    'login.recover.maxTime': 'You have at most 3 hours to renew your password',
    'login.recover.question': 'Did you forget your password?',
    'login.recover.recover': 'Recover',
    'login.recover.recoverIt': 'Recover it',
    'login.recover.emailRequired': 'Email is required',
    'login.recover.emailInvalid': 'Enter a valid email',
    'login.submit.login': 'Sign in',
    'profile.account.form.email': 'Email',
    'profile.account.form.lastName': 'Last Name',
    'profile.account.form.name': 'First Name',
    'profile.account.form.update': 'Update',
    'profile.account.form.username': 'Username',
    'profile.account.title': 'Account',
    'profile.favourites.table.item': 'Item',
    'profile.favourites.table.search': 'Search',
    'profile.favourites.table.seller': 'Seller',
    'profile.favourites.title': 'Favourites',
    'profile.loading': 'Loading...',
    'profile.close': 'Close',
    'profile.items.active': 'Active Publications',
    'profile.items.all': 'All Publications',
    'profile.items.drafts': 'Drafts',
    'profile.items.paused': 'Paused Publications',
    'profile.items.table.edit': 'Edit',
    'profile.items.table.edit_images': 'Edit Images',
    'profile.items.table.item': 'Item',
    'profile.items.table.name': 'Name',
    'profile.items.table.pause': 'Pause Publication',
    'profile.items.table.picture': 'Picture',
    'profile.items.table.price': 'Price',
    'profile.items.table.publish': 'Publish',
    'profile.items.table.resume': 'Resume Publication',
    'profile.items.table.search': 'Search',
    'profile.items.table.sold': 'Sold',
    'profile.items.table.status': 'Status',
    'profile.items.title': 'Published Items',
    'profile.modal.scores.buyer': 'Buyer',
    'profile.modal.scores.close': 'Close',
    'profile.modal.scores.error': 'Error',
    'profile.modal.scores.lowerBuyer': 'buyer',
    'profile.modal.scores.lowerSeller': 'seller',
    'profile.modal.scores.score': 'Score',
    'profile.modal.scores.scoreBuyer': 'Do you wish to score the buyer now?',
    'profile.modal.scores.scoresFrom': 'Score from',
    'profile.modal.scores.scoringTo': 'Scoring',
    'profile.modal.scores.seller': 'Seller',
    'profile.modal.scores.stillNoScore': 'This sale is yet to be scored',
    'profile.modal.scores.success': 'Score was successfully submitted',
    'profile.modal.scores.title': 'Seeing Scores',
    'profile.modal.scores.toScore': 'Score',
    'profile.modal.scores.voidContent': 'Empty content',
    'profile.modal.scores.yourScore': 'Your score',
    'profile.password.form.current': 'Current Password',
    'profile.password.form.new': 'New Password',
    'profile.password.form.repeat': 'Repeat New Password',
    'profile.password.title': 'Password',
    'profile.public.buyer': 'buyer',
    'profile.public.nopurchases': 'has not bought any item yet',
    'profile.public.nosales': 'has not sold an item yet',
    'profile.public.score.buyer': 'Score as a buyer',
    'profile.public.score.buyer.acceptance': 'Acceptance score',
    'profile.public.score.buyer.acceptedPurchases': 'Approved Purchases',
    'profile.public.score.buyer.rejectedPurchases': 'Rejected Purchases',
    'profile.public.score.seller': 'Score as a seller',
    'profile.public.score.seller.approvedSales': 'Approved Sales',
    'profile.public.score.seller.recommendation': 'Recommendation score',
    'profile.public.score.seller.rejectedSales': 'Rejected Sales',
    'profile.public.seller': 'seller',
    'profile.public.seller.items': 'Published Items',
    'profile.public.viewProfileOf': 'View profile as a',
    'profile.purchases.approved': 'Approved Purchases',
    'profile.purchases.declined': 'Declined Purchases',
    'profile.purchases.pending': 'Pending Purchases',
    'profile.purchases.seeScores': 'See scores',
    'profile.purchases.seller': 'Seller',
    'profile.purchases.table.date': 'Date',
    'profile.purchases.table.item': 'Item',
    'profile.purchases.table.score': 'Score',
    'profile.purchases.table.search': 'Search',
    'profile.purchases.table.seller': 'Seller',
    'profile.purchases.title': 'Purchases',
    'profile.sales.approved': 'Approved Sales',
    'profile.sales.danger': 'REJECT',
    'profile.sales.declined': 'Declined Sales',
    'profile.sales.nothing': 'Nothing Found',
    'profile.sales.pending': 'Pending Sales',
    'profile.sales.seeScores': 'See scores',
    'profile.sales.sell.approve': 'approve',
    'profile.sales.sell.approved': 'Purchase Approved',
    'profile.sales.sell.approving': 'approving your sell',
    'profile.sales.sell.confirmation': 'Are you sure you want to',
    'profile.sales.sell.confirmation2': 'this purchase?',
    'profile.sales.sell.error': 'There was a problem',
    'profile.sales.sell.pause': 'pause',
    'profile.sales.sell.pauseBtn': 'Pause Publication',
    'profile.sales.sell.pausing': 'pausing your publication',
    'profile.sales.sell.publishConfirmation': 'Are your sure you wish to publish this item?',
    'profile.sales.sell.published': 'Published',
    'profile.sales.sell.publishing': 'publishing your item',
    'profile.sales.sell.reject': 'reject',
    'profile.sales.sell.rejected': 'Purchase Rejected',
    'profile.sales.sell.rejecting': 'rejecting your sale',
    'profile.sales.sell.resume': 'resume',
    'profile.sales.sell.resumeBtn': 'Resume Publication',
    'profile.sales.sell.resuming': 'resuming your publication',
    'profile.sales.sell.success': 'Your action was successfully executed',
    'profile.sales.success': 'APPROVE',
    'profile.sales.table.buyer': 'Buyer',
    'profile.sales.table.date': 'Date',
    'profile.sales.table.item': 'Item',
    'profile.sales.table.score': 'Scores',
    'profile.sales.table.search': 'Search',
    'profile.sales.title': 'Sales',
    'register.form.createAccount': 'Create Account',
    'register.form.hasAccount': 'Already have an account?',
    'register.form.login': 'Click here',
    'register.form.mail': 'E-mail',
    'register.form.name': 'Name',
    'register.form.nameRequired': 'Name is Required',
    'register.form.nameShort': 'Name is too Short',
    'register.form.nameLong': 'Name is too Long',
    'register.form.namePattern': 'Name should only contain letters',
    'register.form.surnameRequired': 'Surname is Required',
    'register.form.surnameShort': 'Surname is too Short',
    'register.form.surnameLong': 'Surname is too Long',
    'register.form.surnamePattern': 'Surname should only contain letters',
    'register.form.emailRequired': 'Email is Required',
    'register.form.emailInvalid': 'Enter a valid email',
    'register.form.usernameRequired': 'Username is Required',
    'register.form.usernameShort': 'Username is too short',
    'register.form.usernameLong': 'Username is too long',
    'register.form.usernamePattern': 'Username should only contain letters, numbers - or _',
    'register.form.passwordRequired': 'Password is Required',
    'register.form.passwordShort': 'Password is too short',
    'register.form.passwordLong': 'Password is too long',
    'register.form.passwordMatch': 'Passwords do not match',

    'register.form.repeatPassword': 'Repeat password',
    'register.form.surname': 'Surname',
    'search.filters.applied': 'Applied Filters',
    'search.filters.categories': 'Categories',
    'search.filters.categories.apply': 'Apply',
    'search.filters.order.alphabetic': 'Alphabetic',
    'search.filters.order.by': 'Order by',
    'search.filters.order.priceAsc': 'Lowest Price',
    'search.filters.order.priceDesc': 'Highest Price',
    'search.filters.order.recentAsc': 'Least Recent',
    'search.filters.order.recentDesc': 'Most Recent',
    'search.filters.order.soldAsc': 'Least Sold',
    'search.filters.order.soldDesc': 'Most Sold',
    'search.filters.pagination.modifiers.amount': 'Items',
    'search.filters.pagination.modifiers.of': 'of',
    'search.filters.pagination.modifiers.perPage': 'results per page',
    'search.filters.pagination.modifiers.showing': 'Showing',
    'search.filters.pagination.pagers.first': 'First',
    'search.filters.pagination.pagers.last': 'Last',
    'search.filters.pagination.pagers.next': 'Next',
    'search.filters.pagination.pagers.previous': 'Previous',
    'search.filters.price': 'Price',
    'search.filters.price.between': 'Between {{minPrice}} and {{maxPrice}}',
    'search.filters.price.from': 'From {{minPrice}}',
    'search.filters.price.max': 'Max',
    'search.filters.price.min': 'Min',
    'search.filters.price.to': 'Up to {{maxPrice}}',
    'search.none': 'No items found for this search',
    'search.searchResults': 'Search Results',
    'sell.back': 'Go Back',
    'sell.skip': 'Skip',
    'sell.edit.details.success': 'Congratulations! Your item was successfully updated.',
    'sell.edit.return': 'Return to My Items',
    'sell.field.category': 'Category',
    'sell.field.contactEmail': 'Contact Email',
    'sell.field.description': 'Description',
    'sell.field.name': 'Name',
    'sell.field.nameRequired': 'Name is Required',
    'sell.field.nameShort': 'Name is too short',
    'sell.field.nameLong': 'Name is too long',
    'sell.field.pictures': 'Pictures',
    'sell.field.price': 'Price',
    'sell.field.priceRequired': 'Price is Required',
    'sell.field.priceNegative': 'Price cannot be negative',
    'sell.field.priceStep': 'Price should have up to 2 decimal step',
    'sell.field.publish': 'Publish Now',
    'sell.field.radio.no': 'No',
    'sell.field.radio.yes': 'Yes',
    'sell.field.status.new': 'New',
    'sell.field.status.title': 'Status',
    'sell.field.status.used': 'Used',
    'sell.field.used': 'Used',
    'sell.headline': 'Sell An Item',
    'sell.image.upload.drag': 'Drag your images or click to select them',
    'sell.image.upload.headline': 'Upload pictures for',
    'sell.ok': 'Submit and Upload Images',
    'sell.okImages': 'Upload Images',
    'sell.pickCategory': 'Please pick a category',
    'successMessages.buyItem.error': 'Purchase was not successful',
    'successMessages.buyItem.success': 'Congratulations! You have successfully purchased the item',
    'successMessages.changePassword.content': 'Your credentials have been modified',
    'successMessages.changePassword.contentFail': 'Your credentials have not beeen modified',
    'successMessages.changePassword.title': 'Congratulations',
    'successMessages.changePassword.titleFail': 'We\'re sorry',
    'successMessages.publishItem.action': 'Publish another one',
    'successMessages.publishItem.content': 'Your item has been published successfully',
    'successMessages.publishItem.content2': 'Your item has been saved successfully',
    'successMessages.publishItem.title': 'Congratulations',
    'successMessages.toggleFavourite.addError': 'We were unable to add item to favourites',
    'successMessages.toggleFavourite.addSuccess': 'Item has been added to favourites',
    'successMessages.toggleFavourite.removeError': 'We were unable to remove item from favourites',
    'successMessages.toggleFavourite.removeSuccess': 'Item has been removed from favourites',

    'ng.messages.logoutSuccessful': 'Logout Successful',
    'ng.messages.loginSuccessful': 'Login Successful',
    'ng.messages.loginError': 'Incorrect username or password',
    'ng.messages.editSuccess': 'Item successfully updated',
    'ng.messages.editError': 'An error ocurred updating this item',
    'ng.messages.imageSuccess': 'Images have uploaded successfully',
    'ng.messages.imageError': 'An error ocurred uploading the images',
    'ng.messages.reviewSuccess': 'Your review has been submitted',
    'ng.messages.reviewError': 'An error ocurred submitting your review',

    'ng.messages.emailSuccess': 'Email was successfully updated',
    'ng.messages.emailError': 'An error ocurred updating yor email',
    'ng.messages.passwordSuccess': 'Password was successfully updated',
    'ng.messages.passwordError': 'An error ocurred updating yor password',
    'ng.messages.itemSuccess': 'Publication was successfully updated',
    'ng.messages.itemError': 'An error ocurred updating yor publication',
    'ng.messages.purchaseSuccess': 'Purchase successfully approved',
    'ng.messages.purchaseError': 'There was an error approving your purchase',
    'ng.messages.purchaseSuccess2': 'Purchase successfully declined',
    'ng.messages.purchaseError2': 'There was an error declining your purchase',

    'mg.messages.registerSuccess': 'Register successful',
    'mg.messages.registerError': 'There was an error during registration'
  };
});