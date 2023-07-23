fun createAboutList(): ArrayList<CardModel> {
		val appCard = CardModel (
			items = arrayListOf (
			appTitleItem, 
			versionActionItem,
			ActionItem (
			titleRes = R.string.about_changelog_title,
			iconRes = R.drawable.ic_changelog
			),
			ActionItem (
			titleRes = R.string.about_license_title,
			iconRes = R.drawable.ic_license
			) {
			activity.launchActivity<OssLicensesMenuActivity>()
			}
			
			)
		)
		
		val authorCard = CardModel (
			title = R.string.about_author_title,
			items = arrayListOf (
				ActionItem (
					titleRes = R.string.about_author_name,
					descRes = R.string.about_author_location,
					iconRes = R.drawable.ic_dev
				)
			)
		)
		
		val communityCard = CardModel (
			title = R.string.about_social_title,
			items = arrayListOf (
				ActionItem (
					titleRes = R.string.about_facebook_label,
					iconRes = R.drawable.ic_facebook
				) {},
				
			)
		)
		
		val otherCard = CardModel (
			title = R.string.about_others,
			items = arrayListOf (
				ActionItem (
					titleRes = R.string.about_support_title,
					iconRes = R.drawable.ic_apoyar
				) {},
				ActionItem (
					titleRes = R.string.about_share_title,
					iconRes = R.drawable.ic_share
				) {},
				ActionItem (
					titleRes = R.string.about_feedback_title,
					iconRes = R.drawable.ic_coment
				) {}
			)
		)
		
		val moreCard = CardModel (
			items = arrayListOf (
				ActionItem (
					titleRes = R.string.about_more_apps_title,
					iconRes = R.drawable.ic_apps
				) {}
			)
		)
	return cardListOf(
	appCard,
	authorCard,
	communityCard,
	otherCard,
	moreCard
	)
}		