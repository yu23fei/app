package com.paisi.yufei.yuyu.utils

import com.paisi.yufei.yuyu.model.*
import kotlinx.coroutines.delay
import java.util.*
import kotlin.random.Random

/**
 * 模拟数据工具类
 * 为赛事MVP提供完整的Mock数据和AI模拟逻辑
 */
object MockDataUtils {

    // 预置大学生热门旅游城市
    private val cities = listOf("重庆", "成都", "西安")

    // 城市对应的景点数据库
    private val spotDatabase = mapOf(
        "重庆" to listOf(
            Spot("1001", "洪崖洞", "重庆", "渝中区嘉滨路88号", 29.56301f, 106.57402f, "巴渝传统建筑风貌区，夜景璀璨夺目", 0.0, 120, "地标", "学生证半价，晚上7点后人流量大，建议错峰游览"),
            Spot("1002", "解放碑", "重庆", "渝中区民族路177号", 29.55876f, 106.57712f, "重庆市中心地标，商业步行街", 0.0, 60, "地标", "免费参观，周边美食多，适合拍照打卡"),
            Spot("1003", "磁器口古镇", "重庆", "沙坪坝区磁南街1号", 29.58412f, 106.45156f, "千年古镇，巴渝文化缩影", 10.0, 180, "古镇", "有学生优惠，小吃推荐陈麻花、毛血旺"),
            Spot("1004", "长江索道", "重庆", "渝中区新华路151号", 29.56123f, 106.57891f, "空中巴士，横跨长江", 35.0, 30, "交通", "建议非高峰时段乘坐，学生票30元"),
            Spot("1005", "李子坝轻轨站", "重庆", "渝中区桂花园路16号", 29.56789f, 106.55432f, "轻轨穿楼奇观", 0.0, 45, "地标", "免费参观，最佳拍摄点在楼下观景台"),
            Spot("1006", "武隆天生三桥", "重庆", "武隆区仙女山镇", 29.32145f, 107.89123f, "世界自然遗产，喀斯特地貌奇观", 135.0, 360, "自然风光", "学生票67.5元，距市区较远，建议跟团"),
            Spot("1007", "大足石刻", "重庆", "大足区宝顶镇", 29.71234f, 105.78912f, "世界文化遗产，佛教石窟艺术", 120.0, 240, "历史文化", "学生票60元，内容丰富需预留充足时间"),
            Spot("1008", "朝天门广场", "重庆", "渝中区朝东路1号", 29.56543f, 106.58123f, "两江交汇处，游轮码头", 0.0, 60, "地标", "免费参观，可乘船游览两江夜景"),
            Spot("1009", "南山一棵树", "重庆", "南岸区南山公园路", 29.52145f, 106.55432f, "俯瞰重庆夜景最佳地点", 30.0, 90, "观景台", "门票学生价15元，建议傍晚前往"),
            Spot("1010", "白公馆", "重庆", "沙坪坝区歌乐山麓", 29.58765f, 106.45678f, "革命历史纪念馆", 0.0, 120, "红色旅游", "免费参观，需提前预约，了解历史背景"),
            Spot("1011", "渣滓洞", "重庆", "沙坪坝区歌乐山", 29.58123f, 106.45123f, "革命烈士关押地", 0.0, 90, "红色旅游", "免费参观，与白公馆联游，庄严肃穆"),
            Spot("1012", "观音桥步行街", "重庆", "江北区观音桥", 29.57891f, 106.53214f, "西南地区最大商圈", 0.0, 120, "购物", "免费参观，潮流聚集地，美食众多"),
            Spot("1013", "鹅岭二厂文创园", "重庆", "渝中区鹅岭正街1号", 29.56123f, 106.54321f, "工业遗址改造的文艺空间", 0.0, 120, "文创园", "免费参观，电影《从你的全世界路过》取景地"),
            Spot("1014", "四川美术学院", "重庆", "沙坪坝区大学城南路", 29.59123f, 106.23456f, "八大美院之一，涂鸦街", 0.0, 120, "校园", "免费参观，涂鸦艺术墙适合拍照"),
            Spot("1015", "金佛山", "重庆", "南川区三泉镇", 29.01234f, 107.12345f, "国家5A级景区，原始森林", 100.0, 300, "自然风光", "学生票50元，夏季避暑胜地"),
            Spot("1016", "统景温泉", "重庆", "渝北区统景镇", 29.87654f, 106.87654f, "西部第一温泉", 128.0, 180, "休闲", "学生票64元，水质好，环境优美"),
            Spot("1017", "弹子石老街", "重庆", "南岸区弹子石", 29.55432f, 106.56789f, "依山而建的老街区", 0.0, 90, "古镇", "免费参观，可看两江交汇，夜景不错"),
            Spot("1018", "重庆动物园", "重庆", "九龙坡区杨家坪西郊一村1号", 29.54321f, 106.51234f, "大熊猫等珍稀动物", 25.0, 180, "亲子", "学生票12.5元，有熊猫馆"),
            Spot("1019", "黄桷坪涂鸦街", "重庆", "九龙坡区黄桷坪", 29.53214f, 106.50123f, "千米长的涂鸦艺术墙", 0.0, 90, "艺术", "免费参观，文艺青年必去"),
            Spot("1020", "湖广会馆", "重庆", "渝中区东水门正街4号", 29.56123f, 106.57891f, "清代移民会馆建筑群", 25.0, 90, "历史文化", "学生票12.5元，建筑精美"),
        ),
        "成都" to listOf(
            Spot("2001", "宽窄巷子", "成都", "青羊区长顺街", 30.67891f, 104.05432f, "清代古街道，成都慢生活代表", 0.0, 180, "古镇", "免费参观，网红小吃集中地，学生党必打卡"),
            Spot("2002", "锦里古街", "成都", "武侯祠大街231号", 30.63214f, 104.04321f, "三国文化主题古街", 0.0, 120, "古镇", "免费参观，夜景美，但游客多，消费偏高"),
            Spot("2003", "杜甫草堂", "成都", "青羊区青华路37号", 30.67891f, 104.01234f, "唐代诗人杜甫流寓之地", 80.0, 180, "历史文化", "学生票40元，园林清幽，适合静心游览"),
            Spot("2004", "武侯祠", "成都", "武侯祠大街231号", 30.63214f, 104.04321f, "纪念诸葛亮的祠堂", 60.0, 120, "历史文化", "学生票30元，与锦里相邻，可联票购买"),
            Spot("2005", "大熊猫繁育研究基地", "成都", "成华区熊猫大道1375号", 30.71234f, 104.12345f, "全球最大的大熊猫保护机构", 58.0, 240, "亲子", "学生票29元，务必早起（7:30前）看熊猫最活跃"),
            Spot("2006", "春熙路", "成都", "锦江区春熙路", 30.65432f, 104.08765f, "成都最繁华的商业步行街", 0.0, 120, "购物", "免费参观，IFS爬楼熊猫是标志"),
            Spot("2007", "IFS国际金融中心", "成都", "锦江区红星路三段1号", 30.65432f, 104.08765f, "成都地标性建筑", 0.0, 60, "地标", "7楼露台的大熊猫雕塑是网红打卡点"),
            Spot("2008", "太古里", "成都", "锦江区中纱帽街8号", 30.65432f, 104.08765f, "开放式低密度商业区", 0.0, 120, "购物", "免费参观，融合传统与现代，品牌齐全"),
            Spot("2009", "文殊院", "成都", "青羊区文殊院街66号", 30.67891f, 104.06789f, "川西著名佛教寺院", 0.0, 120, "宗教", "免费参观，可求签，素斋有名"),
            Spot("2010", "金沙遗址博物馆", "成都", "青羊区金沙遗址路2号", 30.68912f, 104.01234f, "商周时期古蜀国都邑", 80.0, 180, "历史文化", "学生票40元，太阳神鸟金饰是镇馆之宝"),
            Spot("2011", "青城山", "成都", "都江堰市青城山镇", 30.91234f, 103.61234f, "道教发源地之一", 80.0, 360, "自然风光", "学生票40元，分前山（人文）后山（自然），建议选其一"),
            Spot("2012", "都江堰水利工程", "成都", "都江堰市公园路", 31.01234f, 103.61234f, "世界文化遗产，古代无坝引水工程", 80.0, 180, "历史文化", "学生票40元，感受古人智慧，建议请讲解"),
            Spot("2013", "西岭雪山", "成都", "大邑县西岭镇", 30.81234f, 103.31234f, "成都市第一高峰", 120.0, 300, "自然风光", "学生票60元，冬季滑雪，夏季避暑"),
            Spot("2014", "黄龙溪古镇", "成都", "双流区黄龙溪镇", 30.41234f, 103.81234f, "中国十大水乡古镇之一", 0.0, 180, "古镇", "免费参观，可玩水，夏天热闹，注意防晒"),
            Spot("2015", "人民公园", "成都", "青羊区少城路12号", 30.67891f, 104.05432f, "成都市中心大型公园", 0.0, 120, "休闲", "免费参观，鹤鸣茶社体验盖碗茶，感受慢生活"),
            Spot("2016", "IFS负一楼方所书店", "成都", "锦江区中纱帽街8号", 30.65432f, 104.08765f, "最美地下书店", 0.0, 120, "文化", "免费参观，设计感强，文艺气息浓厚"),
            Spot("2017", "建设路小吃街", "成都", "成华区建设路", 30.67891f, 104.08765f, "本地人最爱的小吃一条街", 0.0, 120, "美食", "免费参观，价格亲民，推荐烤脑花、冰粉"),
            Spot("2018", "东郊记忆", "成都", "成华区建设南支路4号", 30.65432f, 104.12345f, "工业遗址改造的文化创意园区", 0.0, 120, "文创园", "免费参观，音乐、艺术、潮牌聚集地"),
            Spot("2019", "浣花溪公园", "成都", "青羊区青华路9号", 30.67891f, 104.02345f, "成都市内最大的开放性公园", 0.0, 120, "休闲", "免费参观，景色优美，适合散步"),
            Spot("2020", "环球中心", "成都", "高新区天府大道北段1700号", 30.55432f, 104.08765f, "世界最大单体建筑", 0.0, 120, "地标", "免费参观，内部有商场、酒店、水上乐园"),
        ),
        "西安" to listOf(
            Spot("3001", "兵马俑", "西安", "临潼区秦陵北路", 34.38123f, 109.27891f, "世界第八大奇迹，秦始皇陵陪葬坑", 120.0, 240, "历史文化", "学生票60元，强烈建议请官方讲解，否则看不懂"),
            Spot("3002", "大雁塔", "西安", "雁塔区慈恩寺西街66号", 34.22145f, 108.96123f, "玄奘藏经译经之处", 50.0, 120, "历史文化", "登塔另收费，北广场音乐喷泉免费，晚上更壮观"),
            Spot("3003", "陕西历史博物馆", "西安", "雁塔区小寨东路91号", 34.23456f, 108.94321f, "中国第一座大型现代化国家级博物馆", 0.0, 180, "博物馆", "免费但需提前预约，周一闭馆，珍宝库另收费"),
            Spot("3004", "回民街", "西安", "莲湖区西羊市46号", 34.26123f, 108.94123f, "西安著名美食文化街区", 0.0, 180, "美食", "免费参观，小吃种类多，但游客区价格虚高，往深处走更实惠"),
            Spot("3005", "钟楼", "西安", "莲湖区东西南北四条大街交汇处", 34.26123f, 108.94123f, "西安市中心地标", 30.0, 60, "地标", "登楼票价不贵，可俯瞰四条大街"),
            Spot("3006", "鼓楼", "西安", "莲湖区北院门1号", 34.26123f, 108.94123f, "与钟楼遥相呼应", 35.0, 60, "地标", "登楼可看仿古表演，与回民街相连"),
            Spot("3007", "城墙", "西安", "环城南路西段78号", 34.25432f, 108.93214f, "现存最完整的古代城垣", 54.0, 180, "地标", "学生票27元，可骑行，建议租自行车环绕一圈"),
            Spot("3008", "大唐芙蓉园", "西安", "曲江新区芙蓉西路99号", 34.21234f, 108.97891f, "仿唐皇家园林", 120.0, 180, "园林", "学生票60元，晚上的水幕电影值得一看"),
            Spot("3009", "华清宫", "西安", "临潼区华清路38号", 34.37891f, 109.27891f, "唐玄宗与杨贵妃的行宫", 120.0, 180, "历史文化", "学生票60元，温泉水质好，有大型实景演出"),
            Spot("3010", "永兴坊", "西安", "新城区中山门里100米", 34.26123f, 108.94123f, "关中民俗文化美食街", 0.0, 180, "美食", "免费参观，比回民街人少，小吃同样丰富"),
            Spot("3011", "小雁塔", "西安", "碑林区友谊西路72号", 34.25123f, 108.94123f, "荐福寺塔，关中八景之一", 0.0, 90, "历史文化", "免费参观，环境清幽，游客相对较少"),
            Spot("3012", "碑林博物馆", "西安", "碑林区三学街15号", 34.25432f, 108.94123f, "收藏中国古代碑石时间最早、名碑最多", 65.0, 120, "博物馆", "学生票32.5元，书法爱好者的天堂"),
            Spot("3013", "高家大院", "西安", "莲湖区北院门144号", 34.26123f, 108.94123f, "明清古民居院落", 25.0, 60, "古建筑", "通票包含皮影戏表演，了解非遗文化"),
            Spot("3014", "大明宫国家遗址公园", "西安", "未央区太华南路", 34.38123f, 108.96123f, "盛唐时期的国家象征", 0.0, 180, "遗址", "免费参观，面积巨大，可租电瓶车"),
            Spot("3015", "曲江池遗址公园", "西安", "雁塔区曲江池东路", 34.21234f, 108.97891f, "唐代曲江胜景再现", 0.0, 120, "园林", "免费参观，景色优美，适合散步跑步"),
            Spot("3016", "陕西自然博物馆", "西安", "长安区雁展路6号", 34.21234f, 108.97891f, "自然科学主题博物馆", 0.0, 120, "博物馆", "免费参观，恐龙化石展区很受小朋友欢迎"),
            Spot("3017", "汉城湖", "西安", "未央区凤城二路27号", 34.37891f, 108.94123f, "汉代漕运明渠遗址", 0.0, 120, "园林", "免费参观，可乘船，有汉服表演"),
            Spot("3018", "西安博物院", "西安", "碑林区友谊西路72号", 34.25123f, 108.94123f, "集博物馆、名胜古迹、园林为一体", 0.0, 120, "博物馆", "免费参观，与小雁塔同园，性价比高"),
            Spot("3019", "广仁寺", "西安", "莲湖区西北一路1号", 34.26123f, 108.94123f, "陕西省唯一藏传佛教寺院", 0.0, 90, "宗教", "免费参观，红墙金瓦，风格独特"),
            Spot("3020", "八路军西安办事处旧址", "西安", "新城区北新街七贤庄1号", 34.26123f, 108.94123f, "抗日战争时期重要联络站", 0.0, 90, "红色旅游", "免费参观，了解革命历史，周一闭馆"),
        )
    )

    /**
     * 模拟AI生成行程
     * 使用协程模拟异步操作，固定延迟2秒
     */
    suspend fun generateTrip(request: TripRequest): Trip {
        delay(2000L) // 模拟AI处理时间

        val citySpots = spotDatabase[request.destination] ?: emptyList()
        if (citySpots.isEmpty()) {
            // 默认使用重庆数据
            return generateDefaultTrip(request)
        }

        // 根据请求参数筛选和排序景点
        var filteredSpots = citySpots.toMutableList()

        // 如果开启早八模式，优先安排早上开门的景点
        if (request.earlyBirdMode) {
            filteredSpots.sortBy { spot ->
                when (spot.name) {
                    "大熊猫繁育研究基地" -> 0 // 7:30开园
                    "武侯祠", "杜甫草堂", "宽窄巷子" -> 1 // 8:00开园
                    else -> 2
                }
            }
        }

        // 控制预算
        val budgetPerDay = request.budget / request.days.toFloat()
        filteredSpots =
            filteredSpots.filter { it.ticketPrice <= budgetPerDay * 2 }.toMutableList() // 筛选门票合适的景点

        // 计算每日行程
        val dailyTrips = mutableListOf<DailyTrip>()
        val spotsPerDay = maxOf(1, (filteredSpots.size / request.days))
        var currentSpotIndex = 0

        for (day in 1..request.days) {
            val daySpots = mutableListOf<Spot>()
            var dailyCost = 0.0f
            var totalDuration = 0

            repeat(spotsPerDay) {
                if (currentSpotIndex < filteredSpots.size) {
                    val spot = filteredSpots[currentSpotIndex]
                    daySpots.add(spot)
                    dailyCost += spot.ticketPrice
                    totalDuration += spot.duration
                    currentSpotIndex++
                }
            }

            // 补充最后一天可能不足的景点
            if (day == request.days && daySpots.size < spotsPerDay && currentSpotIndex < filteredSpots.size) {
                while (daySpots.size < 3 && currentSpotIndex < filteredSpots.size) {
                    val spot = filteredSpots[currentSpotIndex]
                    daySpots.add(spot)
                    dailyCost += spot.ticketPrice
                    totalDuration += spot.duration
                    currentSpotIndex++
                }
            }

            dailyTrips.add(
                DailyTrip(
                    day = day,
                    date = Date(System.currentTimeMillis() + (day - 1) * 24 * 60 * 60 * 1000L),
                    spots = daySpots,
                    dailyCost = dailyCost,
                    commuteDistance = Random.nextFloat() * 10 + 5 // 模拟通勤距离
                )
            )
        }

        return Trip(
            id = UUID.randomUUID().toString(),
            destination = request.destination,
            days = request.days,
            budget = request.budget,
            startDate = request.startDate,
            dailyTrips = dailyTrips,
            generatedAt = System.currentTimeMillis()
        )
    }

    /**
     * 获取可用于替换的景点列表
     * 支持按类型筛选
     */
    fun getReplaceSpots(originalSpot: Spot, filterType: String): List<Spot> {
        val citySpots = spotDatabase[originalSpot.city] ?: emptyList()
        return when (filterType) {
            "budget" -> citySpots.filter { it.ticketPrice < originalSpot.ticketPrice }.take(3)
            "popular" -> citySpots.sortedByDescending { it.duration }.take(3)
            else -> citySpots.shuffled().take(3).filter { it.id != originalSpot.id } // 智能推荐，排除自身
        }.ifEmpty { citySpots.shuffled().take(3).filter { it.id != originalSpot.id } }
    }

    /**
     * 模拟调整行程
     * 根据用户需求优化原行程
     */
    suspend fun adjustTrip(originalTrip: Trip, adjustDemand: String): Trip {
        delay(2000L) // 模拟AI处理时间

        val adjustedDailyTrips = originalTrip.dailyTrips.mapIndexed { index, dailyTrip ->
            var newSpots = dailyTrip.spots.toMutableList()
            var newCost = dailyTrip.dailyCost
            var newDuration = dailyTrip.commuteDistance

            when {
                adjustDemand.contains("减少预算", ignoreCase = true) || adjustDemand.contains("省钱", ignoreCase = true) -> {
                    // 替换为更便宜的景点
                    newSpots = newSpots.map { spot ->
                        val cheaperSpots = getReplaceSpots(spot, "budget")
                        cheaperSpots.firstOrNull() ?: spot
                    }.toMutableList()
                    newCost = newSpots.sumOf { it.ticketPrice }
                }
                adjustDemand.contains("增加美食", ignoreCase = true) || adjustDemand.contains("吃", ignoreCase = true) -> {
                    // 尝试插入一个美食相关景点
                    val foodSpots = spotDatabase[originalTrip.destination]?.filter { it.type == "美食" }?.shuffled()?.take(1) ?: emptyList()
                    if (foodSpots.isNotEmpty() && newSpots.size < 4) {
                        newSpots.add(foodSpots[0])
                        newCost += foodSpots[0].ticketPrice
                    }
                }
                adjustDemand.contains("不要太赶", ignoreCase = true) || adjustDemand.contains("轻松", ignoreCase = true) -> {
                    // 移除一个景点
                    if (newSpots.size > 1) {
                        val removedSpot = newSpots.removeAt(newSpots.size - 1)
                        newCost -= removedSpot.ticketPrice
                        newDuration *= 0.8f // 通勤距离也减少
                    }
                }
                adjustDemand.contains("加点夜生活", ignoreCase = true) || adjustDemand.contains("夜", ignoreCase = true) -> {
                    // 尝试添加一个夜景观光点
                    val nightSpots = spotDatabase[originalTrip.destination]?.filter { it.name.contains("夜景") || it.type == "观景台" }?.shuffled()?.take(1) ?: emptyList()
                    if (nightSpots.isNotEmpty() && newSpots.size < 4) {
                        newSpots.add(nightSpots[0])
                        newCost += nightSpots[0].ticketPrice
                    }
                }
                else -> {
                    // 默认智能调整：随机微调
                    if (Random.nextBoolean() && newSpots.size > 1) {
                        val replaceIndex = Random.nextInt(newSpots.size)
                        val oldSpot = newSpots[replaceIndex]
                        val replacement = getReplaceSpots(oldSpot, "recommend").firstOrNull()
                        if (replacement != null) {
                            newCost = newCost - oldSpot.ticketPrice + replacement.ticketPrice
                            newSpots[replaceIndex] = replacement
                        }
                    }
                }
            }

            dailyTrip.copy(
                spots = newSpots,
                dailyCost = newCost,
                commuteDistance = newDuration
            )
        }

        val adjustedBudget = adjustedDailyTrips.sumOf { it.dailyCost }

        return originalTrip.copy(
            dailyTrips = adjustedDailyTrips,
            budget = adjustedBudget,
            generatedAt = System.currentTimeMillis()
        )
    }

    /**
     * 生成默认行程（当目的地无效时）
     */
    private fun generateDefaultTrip(request: TripRequest): Trip {
        val defaultSpots = spotDatabase["重庆"] ?: emptyList()
        val dailyTrips = (1..request.days).map { day ->
            DailyTrip(
                day = day,
                date = Date(System.currentTimeMillis() + (day - 1) * 24 * 60 * 60 * 1000L),
                spots = defaultSpots.shuffled().take(2),
                dailyCost = 100.0f,
                commuteDistance = 10.0f
            )
        }

        return Trip(
            id = UUID.randomUUID().toString(),
            destination = "重庆",
            days = request.days,
            budget = request.budget,
            startDate = request.startDate,
            dailyTrips = dailyTrips,
            generatedAt = System.currentTimeMillis()
        )
    }
}