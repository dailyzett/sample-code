package org.example.redisexample.domain.res

import org.springframework.data.redis.core.ZSetOperations

data class MembersRankRes(
    val result: List<MembersRankResult>
) {
    data class MembersRankResult(
        val id: Int,
        val name: String,
        val dailyScore: Int,
    )

    companion object {
        fun of(
            leaderBoardInfo: Set<ZSetOperations.TypedTuple<String>>?,
            memberNames: List<String>
        ): MembersRankRes {
            val leaderBoardList = leaderBoardInfo?.toList()
            val result = leaderBoardList
                ?.zip(memberNames)
                ?.map { (leaderBoard, name) ->
                MembersRankResult(
                    leaderBoard.value?.toInt() ?: -1,
                    name,
                    leaderBoard.score?.toInt() ?: -1
                )
            } ?: emptyList()

            return MembersRankRes(result)
        }
    }
}