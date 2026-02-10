package com.kuit.archiveatproject.domain.entity

data class Home(
    val firstGreetingMessage: String,
    val secondGreetingMessage: String,
    val tabs: List<HomeTab>,
    val contentCards: List<HomeContentCard>,
    val contentCollectionCards: List<HomeContentCollectionCard>
)

data class HomeTab(
    val type: HomeTabType,
    val label: String,
    val subMessage: String
)

data class HomeContentCard(
    val newsletterId: Long,
    val tabType: HomeTabType,   // 서버 tabLabel -> type 매핑 필요
    val tabLabel: String,       // 화면에 표시할 라벨
    val cardType: HomeCardType,
    val title: String,
    val smallCardSummary: String,
    val mediumCardSummary: String,
    val thumbnailUrl: String?
)

data class HomeContentCollectionCard(
    val collectionId: Long,
    val tabType: HomeTabType,
    val tabLabel: String,
    val cardType: HomeCardType,
    val title: String,
    val smallCardSummary: String,
    val mediumCardSummary: String,
    val thumbnailUrls: List<String>
)

enum class HomeTabType {
    ALL, INSPIRATION, DEEP_DIVE, GROWTH, VIEW_EXPANSION;

    companion object {
        fun from(raw: String?): HomeTabType =
            runCatching { valueOf(raw?.trim().orEmpty()) }.getOrElse { ALL }
    }
}

enum class HomeCardType(val label: String) {
    AI_SUMMARY("AI 요약"),
    COLLECTION("컬렉션");

    companion object {
        fun fromLabel(label: String): HomeCardType =
            entries.firstOrNull { it.label == label.trim() } ?: AI_SUMMARY
    }
}