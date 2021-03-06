package com.ngx.boot.excelOperation;


import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Random;

public class GeneratePerSonInfo {
    //百家姓
    private static final String[] Surname = {"赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈", "褚", "卫", "蒋", "沈", "韩", "杨", "朱", "秦", "尤", "许",
            "何", "吕", "施", "张", "孔", "曹", "严", "华", "金", "魏", "陶", "姜", "戚", "谢", "邹", "喻", "柏", "水", "窦", "章", "云", "苏", "潘", "葛", "奚", "范", "彭", "郎",
            "鲁", "韦", "昌", "马", "苗", "凤", "花", "方", "俞", "任", "袁", "柳", "酆", "鲍", "史", "唐", "费", "廉", "岑", "薛", "雷", "贺", "倪", "汤", "滕", "殷",
            "罗", "毕", "郝", "邬", "安", "常", "乐", "于", "时", "傅", "皮", "卞", "齐", "康", "伍", "余", "元", "卜", "顾", "孟", "平", "黄", "和",
            "穆", "萧", "尹", "姚", "邵", "湛", "汪", "祁", "毛", "禹", "狄", "米", "贝", "明", "臧", "计", "伏", "成", "戴", "谈", "宋", "茅", "庞", "熊", "纪", "舒",
            "屈", "项", "祝", "董", "梁", "杜", "阮", "蓝", "闵", "席", "季", "麻", "强", "贾", "路", "娄", "危", "江", "童", "颜", "郭", "梅", "盛", "林", "刁", "钟",
            "徐", "邱", "骆", "高", "夏", "蔡", "田", "樊", "胡", "凌", "霍", "虞", "万", "支", "柯", "昝", "管", "卢", "莫", "经", "房", "裘", "缪", "干", "解", "应",
            "宗", "丁", "宣", "贲", "邓", "郁", "单", "杭", "洪", "包", "诸", "左", "石", "崔", "吉", "钮", "龚", "程", "嵇", "邢", "滑", "裴", "陆", "荣", "翁", "荀",
            "羊", "于", "惠", "甄", "曲", "家", "封", "芮", "羿", "储", "靳", "汲", "邴", "糜", "松", "井", "段", "富", "巫", "乌", "焦", "巴", "弓", "牧", "隗", "山",
            "谷", "车", "侯", "宓", "蓬", "全", "郗", "班", "仰", "秋", "仲", "伊", "宫", "宁", "仇", "栾", "暴", "甘", "钭", "厉", "戎", "祖", "武", "符", "刘", "景",
            "詹", "束", "龙", "叶", "幸", "司", "韶", "郜", "黎", "蓟", "溥", "印", "宿", "白", "怀", "蒲", "邰", "从", "鄂", "索", "咸", "籍", "赖", "卓", "蔺", "屠",
            "蒙", "池", "乔", "阴", "郁", "胥", "能", "苍", "双", "闻", "莘", "党", "翟", "谭", "贡", "劳", "逄", "姬", "申", "扶", "堵", "冉", "宰", "郦", "雍", "却",
            "璩", "桑", "桂", "濮", "牛", "寿", "通", "边", "扈", "燕", "冀", "浦", "尚", "农", "温", "别", "庄", "晏", "柴", "瞿", "阎", "充", "慕", "连", "茹", "习",
            "宦", "艾", "鱼", "容", "向", "古", "易", "慎", "戈", "廖", "庾", "终", "暨", "居", "衡", "步", "都", "耿", "满", "弘", "匡", "国", "文", "寇", "广", "禄",
            "阙", "东", "欧", "殳", "沃", "利", "蔚", "越", "夔", "隆", "师", "巩", "厍", "聂", "晁", "勾", "敖", "融", "冷", "訾", "辛", "阚", "那", "简", "饶", "空",
            "曾", "毋", "沙", "乜", "养", "鞠", "须", "丰", "巢", "关", "蒯", "相", "查", "后", "荆", "红", "游", "郏", "竺", "权", "逯", "盖", "益", "桓", "公", "仉",
            "督", "岳", "帅", "缑", "亢", "况", "郈", "有", "琴", "归", "海", "晋", "楚", "闫", "法", "汝", "鄢", "涂", "钦", "商", "牟", "佘", "佴", "伯", "赏", "墨",
            "哈", "谯", "篁", "年", "爱", "阳", "佟", "言", "福", "南", "火", "铁", "迟", "漆", "官", "冼", "真", "展", "繁", "檀", "祭", "密", "敬", "揭", "舜", "楼",
            "疏", "冒", "浑", "挚", "胶", "随", "高", "皋", "原", "种", "练", "弥", "仓", "眭", "蹇", "覃", "阿", "门", "恽", "来", "綦", "召", "仪", "风", "介", "巨",
            "木", "京", "狐", "郇", "虎", "枚", "抗", "达", "杞", "苌", "折", "麦", "庆", "过", "竹", "端", "鲜", "皇", "亓", "老", "是", "秘", "畅", "邝", "还", "宾",
            "闾", "辜", "纵", "侴", "万俟", "司马", "上官", "欧阳", "夏侯", "诸葛", "闻人", "东方", "赫连", "皇甫", "羊舌", "尉迟", "公羊", "澹台", "公冶", "宗正",
            "濮阳", "淳于", "单于", "太叔", "申屠", "公孙", "仲孙", "轩辕", "令狐", "钟离", "宇文", "长孙", "慕容", "鲜于", "闾丘", "司徒", "司空", "兀官", "司寇",
            "南门", "呼延", "子车", "颛孙", "端木", "巫马", "公西", "漆雕", "车正", "壤驷", "公良", "拓跋", "夹谷", "宰父", "谷梁", "段干", "百里", "东郭", "微生",
            "梁丘", "左丘", "东门", "西门", "南宫", "第五", "公仪", "公乘", "太史", "仲长", "叔孙", "屈突", "尔朱", "东乡", "相里", "胡母", "司城", "张廖", "雍门",
            "毋丘", "贺兰", "綦毋", "屋庐", "独孤", "南郭", "北宫", "王孙"};

    private static final String[] major = {"软件工程", "临床医学", "网络工程", "航天航空", "金融学", "交通运输", "社会学", "新闻传播", "海洋地质",
            "热能与动力工程", "生物技术", "生物医学工程", "物流管理", "工商管理", "通信工程", "土木工程", "行政管理", "古典文学", "数理统计", "光学"};


    private static final String[][] course = {
            {"马克思主义理论", "大学外语", "高等数学", "大学物理", "物理实验", "线性代数", "概率论与数理统计", "程序设计语言",
                    "数据结构", "离散数学", "操作系统", "编译技术", "软件工程概论", "统一建模语言", "软件体系结构", "软件需求",
                    "软件项目管理", "数字电路", "数字逻辑", "计算机体系结构", "计算机网络", "算法分析与设计", "数据库系统原理", "编译原理",
                    "毕业实习", "课程设计", "计算机工程实践", "毕业设计"},

            {"人体解剖学", "组织学与胚胎学", "生物化学", "神经生物学", "生理学", "医学微生物学", "医学免疫学", "病理学",
                    "药理学", "人体形态学实验", "医学生物学实验", "医学机能学实验", "病原生物学与免疫学实验", "诊断学", "内科学", "外科学",
                    "妇产科学", "儿科学", "循证医学", "卫生法学", "医学伦理学", "医学心理学", "医患沟通与技巧", "马克思主义基本原理",
                    "毕业实习", "课程设计", "医学实践", "毕业设计"},


            {"线性代数", "概率论", "电路分析基础", "电子电路基础", "数字电路基础", "电子线路CAD", "电子技术实验", "电子技术课题设计",
                    "离散数学", "汇编语言程序设计", "计算机组成原理", "单片机原理", "接口技术", "操作系统原理", "数据结构", "面向对象程序设计",
                    "计算机网络", "现代通信技术", "数据库系统原理", "计算机图形学", "编译原理", "科技英语", "综合布线技术", "网络的组建与设计",
                    "毕业实习", "课程设计", "网络工程实践", "毕业设计"},


            {"空气动力学", "飞行器结构力学", "材料力学", "航空航天概论", "机械设计基础", "电路与电子学", "自动控制原理", "工程热力学",
                    "飞行器总体设计", "飞行器结构设计", "传热学", "燃烧学", "流体力学", "结构强度", "材料与制造工艺", "航空发动机",
                    "飞行控制", "通信与导航", "风洞试验", "可靠性与质量控制", "安全救生", "环境控制", "航空仪表", "航空宇航制造工程",
                    "毕业实习", "课程设计", "航天航空实践", "毕业设计"},


            {"微观经济学", "宏观经济学", "会计学", "财政学", "统计学", "计量经济学", "货币银行学", "国际金融学",
                    "金融市场", "商业银行经营学", "投资银行学", "国际贸易", "保险学", "证券投资学", "金融衍生工具", "金融经济学",
                    "现代货币理论", "国际货币制度概论", "证券投资学", "国际贸易实务", "英语精读", "微积分", "线性代数", "概率论与数理统计",
                    "毕业实习", "课程设计", "金融学实践", "毕业设计"},


            {"城市轨道交通运营管理", "电子商务系统规划与设计", "交通规划", "运输组织学", "交通规划", "运输组织学", "交通安全工程", "城市轨道交通规划与设计",
                    "道路交通管理与控制", "铁路行车组织", "高速铁路纵横", "铁路史话", "交通博览", "铁路行车组织", "道路工程", "管理运筹学",
                    "铁路站场与枢纽", "交通运输设备", "交通运输法规", "理论力学", "交通工程学", "智能交通系统", "交通港站与枢纽", "工程图学",
                    "毕业实习", "课程设计", "交通运输实践", "毕业设计"},


            {"近代历史", "中国近代历史", "宏观经济学", "微观经济学", "法学概论", "政治学概论", "管理学概论", "心理学概论",
                    "伦理学概论", "逻辑学概论", "民族学概论", "统计学", "社会问题", "社会心理学", "发展社会学", "知识社会学",
                    "宗教社会学", "教育社会学", "政治社会学", "经济社会学", "文化社会学", "法律社会学", "组织社会学", "家庭社会学",
                    "毕业实习", "课程设计", "社会学实践", "毕业设计"},


            {"新闻学概论", "传播学", "新闻采写", "现场记者报道", "广播电视新闻学", "电视摄影", "新闻编辑", "传媒心理学",
                    "新闻作品评析", "传媒经济学", "媒介伦理法规", "舆论学", "广播电视史", "广播电视采访", "广播电视写作", "电视画面编辑",
                    "广播电视评论", "融合新闻学", "非线性编辑", "电视摄像", "大众传播史", "传播研究方法", "跨文化传播", "调查软件与应用",
                    "毕业实习", "课程设计", "新闻传播实践", "毕业设计"},


            {"海洋学", "化学", "生物学", "地质学", "数学", "海洋科学导论", "流体力学", "物理海洋学",
                    "化学海洋学", "海洋地质学", "海洋生物学", "海洋水文气象", "海洋遥感技术", "海洋管理概论", "海洋技术", "船舶与港口工程",
                    "航道测量学", "海洋要素调查", "化学海洋学", "生物海洋学", "地质海洋学", "渔业海洋学", "河口生态学", "珊瑚礁生态学",
                    "毕业实习", "课程设计", "海洋地质实践", "毕业设计"},


            {"高等数学", "线性代数", "概率论与数理统计", "大学物理", "大学物理实验", "普通化学及实验", "工程图学", "微机原理与接口技术",
                    "理论力学", "材料力学", "流体力学", "电工学", "金属工艺学", "机械原理", "机械设计", "互换性与技术测量",
                    "制造技术基础", "材料成型技术基础", "计算机控制技术", "单片机原理与应用", "工程热力学", "传热学", "热力测试技术", "内燃机设计",
                    "毕业实习", "课程设计", "热能与动力工程实践", "毕业设计"},


            {"微生物学", "细胞生物学", "遗传学", "生物化学", "分子生物学", "基因工程", "细胞工程", "微生物工程",
                    "生化工程", "生物工程下游技术", "发酵工程", "无机化学", "有机化学", "分析化学", "植物学", "动物学",
                    "普通遗传学", "仪器分析", "酿造工艺学", "分离工程", "微生物分类学", "生物制药学", "专业英语", "生物统计学",
                    "毕业实习", "课程设计", "生物技术实践", "毕业设计"},


            {"模拟电子技术", "数字电子技术", "人体解剖学", "生理学", "基础生物学", "生物化学", "信号与系统", "数据结构",
                    "数据库原理", "数字信号处理", "EDA技术", "数字图像处理", "自动控制原理", "医学成像原理", "生物信息学", "微生物学",
                    "细胞生物学", "遗传学", "生物化学", "分子生物学", "基因工程", "细胞工程", "微生物工程", "生化工程",
                    "毕业实习", "课程设计", "生物医学工程实践", "毕业设计"},

            {"物流概论", "物流规划与设计", "采购管理", "供应管理", "采购项目管理", "运输管理", "仓储管理", "配送管理",
                    "国际物流学", "国际贸易理论与实务", "采购过程演练", "运输实务", "仓储管理实务", "物流配送中心设计", "国际物流实务", "成功学",
                    "创新学", "素质拓展训练", "项目管理", "运输系统管理", "仓储系统管理", "配送系统管理", "国际系统学", "物流系统学",
                    "毕业实习", "课程设计", "物流管理实践", "毕业设计"},

            {"高等数学", "线性代数", "概率论与数理统计", "管理学原理", "微观经济学", "宏观经济学", "技术经济学", "管理信息系统",
                    "统计学", "会计学", "中级会计实务", "财务管理", "运筹学", "市场营销", "经济法", "现代公司制概论",
                    "经营管理", "公司金融", "人力资源管理", "企业战略管理", "计算机应用", "电子商务", "国际企业管理", "经济学",
                    "毕业实习", "课程设计", "工商管理实践", "毕业设计"},

            {"电子线路", "数字逻辑电路", "计算机基础", "信号与系统", "数字信号处理", "电磁场", "微波技术", "通信原理",
                    "通信网理论基础", "现代通信技术", "微波通信", "卫星通信", "计算机通信", "扩频通信", "数字音频处理", "VHDL系统硬件设计技术",
                    "嵌入式实时系统", "保密安全", "密码技术", "通信新技术", "系统硬件设计技术", "数据结构", "操作系统", "数据库",
                    "毕业实习", "课程设计", "通信工程实践", "毕业设计"},

            {"大学英语", "高等数学", "线性代数", "概率论", "建筑力学", "结构力学", "土木工程制图", "CAD",
                    "理论力学", "结构力学", "材料力学", "土力学", "房屋建筑学", "桥梁工程", "道路工程", "混凝土结构设计",
                    "岩体力学", "流体力学", "弹性力学", "专业英语", "工程管理学", "工程经济学", "桥涵水文", "大跨度桥梁",
                    "毕业实习", "课程设计", "土木工程实践", "毕业设计"},

            {"行政管理学", "市政学", "社会学", "行政领导", "行政决策", "人力资源开发", "人力资源管理", "组织行为学",
                    "西方经济学", "行政法学", "社会调查与统计", "公共政策分析", "公务员制度概论", "行政公文", "机关管理", "办公自动化",
                    "政府机关事务", "社会调查", "电子政务", "行政写作", "行政学", "行政理论研究", "公共政策", "管理学",
                    "毕业实习", "课程设计", "行政管理实践", "毕业设计"},

            {"写作", "古代文学", "中国现代文学史", "中国当代文学史", "教师语言", "文学批评", "文学概论", "语文教学策略",
                    "普通教育学", "语言学概论", "外国文学", "现代汉语", "古代汉语", "语文课程与教学论", "文学名著导读", "发展与教育心理学",
                    "比较文学", "大学英语", "教育政策法规", "教师职业道德", "教育研究方法", "现代教育技术应用", "书法", "语用学",
                    "毕业实习", "课程设计", "古典文学实践", "毕业设计"},

            {"数学分析", "几何代数", "数学实验", "常微分方程", "复变函数", "实变与泛函", "概率论", "数理统计",
                    "抽样调查", "随机过程", "多元统计", "计算机应用基础", "程序设计语言", "数据分析及统计软件", "回归分析", "可靠性数学",
                    "实验设计与质量控制", "计量经济学", "经济预测与决策", "金融数学", "证券投资", "统计分析", "数值分析", "数据结构",
                    "毕业实习", "课程设计", "数理统计实践", "毕业设计"},

            {"发光原理基础", "平板显示", "驱动技术", "液晶电子学", "场致发光显示", "气体放电", "等离子体显示", "真空微电子学",
                    "场发射显示", "大屏显示技术", "电子光学应用", "现代薄膜技术", "显示器件制造技术", "现代显示技术", "通信原理", "网络技术",
                    "应用光学", "物理光学", "激光原理", "导波光学", "辐射度学", "色度学", "数字图像处理", "信号与系统",
                    "毕业实习", "课程设计", "光学工程实践", "毕业设计"}
    };

    private static final String[] score = {"59", "65", "70", "80", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100", "101"};
    private static final Double[] money = {};

    public static String getChineseName() {
        String str = null;
        String name = null;
        int highPos, lowPos;
        Random random = new Random();
        //区码，0xA0打头，从第16区开始，即0xB0=11*16=176,16~55一级汉字，56~87二级汉字
        highPos = (176 + Math.abs(random.nextInt(72)));
        random = new Random();
        //位码，0xA0打头，范围第1~94列
        lowPos = 161 + Math.abs(random.nextInt(94));

        byte[] bArr = new byte[2];
        bArr[0] = (Integer.valueOf(highPos)).byteValue();
        bArr[1] = (Integer.valueOf(lowPos)).byteValue();
//        bArr[1] = (new Integer(lowPos)).byteValue();
        try {
            //区位码组合成汉字
            str = new String(bArr, "GB2312");
            int index = random.nextInt(Surname.length - 1);
            //获得一个随机的姓氏
            name = Surname[index] + str;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return name;
    }

    public static String getSex() {
        int randNum = new Random().nextInt(2) + 1;
        return randNum == 1 ? "男" : "女";
    }

    public static String getMajor() {
        Random random = new Random();
        int pos = random.nextInt(major.length - 1);
//        System.out.println(pos);
        return major[pos];
    }

    public static String getCourse() {
        Random random = new Random();
        int first = random.nextInt(course.length - 1);
        int last = random.nextInt(course[first].length - 1);
        return course[first][last];

    }

    public static Double getScore() {
        Random random = new Random();
        int pos = random.nextInt(score.length - 1);
        return Double.parseDouble(score[pos]);
    }

    public static Double getMoney() {
        double max = 50.0;
        double min = 5.0;
        BigDecimal bigDecimal = new BigDecimal(Math.random() * (max - min) + min);
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Integer getRestaurantNumber() {
        return new Random().nextInt(4) + 1;
    }

    public static Integer getRestaurantWindowNumber() {
        return new Random().nextInt(10) + 1;
    }


}
