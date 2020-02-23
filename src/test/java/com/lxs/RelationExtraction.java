package com.lxs;

import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLSentence;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLWord;
import com.hankcs.hanlp.dependency.IDependencyParser;
import com.hankcs.hanlp.dependency.nnparser.NeuralNetworkDependencyParser;

import java.util.*;

public class RelationExtraction {

    // 测试句子
    public static String s1 = (
        "恒天软件拥有CMMI软件质量管理五级资质及CNAS资质认证，团队聚集了一批技术经验丰富、自主创新能力强的研发队伍，在软件质量保证领域有非常成熟的技术能力，致力于为客户提供贯穿于整个软件开发生命周期的专业软件测试、咨询及评估服务。同时，公司致力于为客户提供专业的一站式企业基础设施和IT运维解决方案，专家团队拥有CNAS国家实验室资质及OCP、CCNP、RHCE、CISP（信息安全人员国家级最高资质），CIIP-A（等保）等个人资质，专注为客户提供权威的国家标准安全测试服务。 \n" +
            "恒天云 \n" +
            "恒天云团队，成立于2010年，专业的研发、运营和服务团队，具备万台虚拟机规模的一线运营能力，先后设计过上百个大中型私有云平台，可提供成熟的从云规划、云迁移、云培训到云运维的全套企业上云标准服务，并与阿里云、华通云等云服务商开展了深度合作。目前已为金融、通讯、教育、政府等多领域客户提供了从公有云、私有云，到混合云的全方位上云选择与上云服务。\n" +
            "IMS（IT运维管理） \n" +
            "IMS团队致力于为客户提供高效、高品质服务，提供专业的一站式企业基础设施和IT运维解决方案。服务能力涵盖服务器、数据库、邮件服务，存储、网络、桌面、中间件、配置管理、安全运维、虚拟化和云服务、服务台管理以及基础设施管理等运维项目。团队服务专家拥有国际通行的专业认证，如OCP、CCNP、RHCE、MCSE、ITIL、ICSD、JIA、JIS等。\n" +
            "SEG（系统安全检测） \n" +
            "安全工程团队主要为国内外客户提供Web系统安全评估，高级渗透测试，紧急安全响应等服务以及自动化安全测试系统等服务。团队拥有中国合格评定国家认可委员会（CNAS）的实验室认可证书，团队安全专家已获得CISP（信息安全人员国家级最高资质），CIIP-A（国家信息安全等级保护认证）等资质。\n" +
            "PEG（软件性能测试） \n" +
            "性能工程团队是恒天旗下专门从事性能工程领域研究与实践的团队，主要为国内外客户提供性能测试、性能调优、性能测试监理、性能测试培训等性能工程领域的服务。恒天性能工程团队已经在性能测试专业化和多个领域测试上积累了丰富经验，已累计完成200余个性能项目。"
    );
    public static String s2 = ("第二届中国国际进口博览会(以下简称：进博会)近日在上海开幕，来自170多个国家和地区的3000多家企业、约50万专业采购商齐聚上海。期间举办的11月6日浙江-德国（欧洲）数字经济和高新技术产业高峰对接会上，浙大网新副总裁兼CTO、恒天软件首席执行官周波先生应邀进行了“数字重塑产业和贸易创新”主题演讲。\n" +
        "当今世界，全球价值链、供应链深入发展，新一轮科技革命和产业变革正处在实现重大突破的历史关口。周波在演讲中回顾了浙江20年的经济发展历程，并将这20年总结为外向型经济高速成长、制造业转型升级和内需市场由“量”向“质”转型三个阶段。周波强调，在以浙江为代表的中国经济发展20年中，信息技术和数字技术的发展起到了巨大的作用。从2000年起我国的内需市场开始逐渐形成，互联网产业积极探索，一大批优秀的创业公司成立，到2010年左右浙江兴起的“机器换人”，再到2015年后我国的“互联网+”走出了自己独特的发展道路，数字技术不断以自身的革新驱动着浙江和中国经济的发展，推动了贸易的转型升级。\n" +
        "在谈到经济外向型发展时期时，周波以浙大网新和美国道富集团共同成立恒天软件为例，讲述那一时期中国科技企业在技术外包的发展情况。在金融危机之后，周波以恒天软件见证并协助CFETS（中国外汇交易中心）构建了中国货币市场金融标准及相关基础核心系统建设为例，阐述了新时期的中国政府、中国企业有了更强的自主意识，中国构建自己的标准和体系必要且迫切。他还分享了恒天软件与农夫山泉等国内品牌、与耐克等国际品牌合作探索中国市场的新零售创新系统，讲述了浙江企业在柔性制造和高效定制化客户服务体系中的大胆尝试。\n" +
        "习近平主席说，进博会“交易的是商品和服务，交流的是文化和理念”。站在新的历史起点，我们应该共建开放合作、创新、共享的世界经济。恒天软件看准欧洲市场，带着技术积极“走出去”。去年在爱尔兰成立了子公司，通过切入Know Your Customer（简称KYC)这一细分领域，利用区块链技术在主要金融机构之间建立联盟链，通过区块链进行交易跟踪和智能合约实现自动化记账支付，帮助欧美金融机构在银行、基金业务实现反洗钱功能。未来公司将在软件开发与创新领域不断探索，以“一带一路”为作为公司海外业务发展的良好契机，积极推广和推动“中国方案”在欧洲落地，以此带动更广阔欧洲业务的发展，为更多国内外企业创造更大的科技价值，在贸易、科技全球化浪潮中开拓新篇章。");
//    public static String st1 = ("IMS团队致力于为客户提供高效、高品质服务，提供专业的一站式企业基础设施和IT运维解决方案");
//    public static String st2 = ("团队服务专家拥有国际通行的专业认证，如OCP、CCNP、RHCE、MCSE、ITIL、ICSD、JIA、JIS等");
//    public static String st3 = ("恒天软件拥有CMMI软件质量管理五级资质及CNAS资质认证，团队聚集了一批技术经验丰富、自主创新能力强的研发队伍，在软件质量保证领域有非常成熟的技术能力，致力于为客户提供贯穿于整个软件开发生命周期的专业软件测试、咨询及评估服务");

    // 句法结构基本参数
    private static CoNLLSentence initalSentence;    // 初始文本
    private static String[] sentenceInput;          // 分隔后的句子集
    private static String curSentence;              // 当前句子
    private static IDependencyParser parser;        // 依存句法分析器
    private static CoNLLWord[] wordArraywithRoot;   // 句法分析结果
    private static int triplenum;                   // 抽取三元组的计数
    private static Set<String> ChinesePassive = new HashSet<String>();
    private static Set<String> ChineseNegative = new HashSet<String>();

    // 当前可能组合的三元组（source, rel, des）
    private static String rel;          // 关系词
    private static int relstart;
    private static int relend;
    private static String source;       // 主语
    private static CoNLLWord csource;   // 主语词
    private static String des;          // 宾语
    private static String sup;
    private static boolean fin1;        // 是否是第一轮动宾结构

    private static void initialParameters(String initialSentence) {
        // 初始化“被动词”集合
        ChinesePassive.add("被");
        ChinesePassive.add("让");
        ChinesePassive.add("给");
        ChinesePassive.add("叫");
        // 初始化“否定词”集合
        ChineseNegative.add("不");
        ChineseNegative.add("否");
        ChineseNegative.add("没");
        ChineseNegative.add("无");
        ChineseNegative.add("非");
        ChineseNegative.add("未");
        ChineseNegative.add("莫");
        ChineseNegative.add("别");

        // 分割句子
        initialSentence = initialSentence.trim();
        sentenceInput = initialSentence.split("!|。");
    }

    private static String findVerbPreSupplement(CoNLLWord verb) {
        List<CoNLLWord> out = initalSentence.findChildren(verb, "状中结构");
        String ans = "";
        int cnt = 0;
        // 获取每一个完整的状中结构
        for (CoNLLWord c:out) {
            // 找到关系词的第一个位置
            CoNLLWord startc = c;
            if (cnt == 0) {
                int cur = findIdx(c);
                relstart = cur<relstart?cur:relstart;
                cnt = 1;
            }
            // 提取介宾结构的状语
            String startword = startc.LEMMA;
            CoNLLWord endc = initalSentence.findOneChildren(startc, "介宾关系");
            if (endc == null) {
                ans += startc.LEMMA;
                continue;
            }
            String endword = endc.LEMMA;
            int i;
            for (i = curSentence.indexOf(startword); i != curSentence.indexOf(endword); i++) {
                ans += curSentence.substring(i, i+1);
            }
            ans += endword;
            break;
        }
        return ans;
    }

    private static String findVerbPostSupplement(CoNLLWord verb) {
        List<CoNLLWord> sup = initalSentence.findChildren(verb);
        String ans = "";
        for (int i = 0; i < sup.size(); i++) {
            CoNLLWord c = sup.get(i);
            if (c.DEPREL.equals("标点符号"))
                break;
            if (c.DEPREL.equals("右附加关系")) {
                ans += c.LEMMA;
                int ii = findIdx(c);
                relend = ii>relend?ii:relend;
            }
        }
        return ans;
    }

    private static String findSubject(CoNLLWord noun, boolean first) {
        String ans = "";
        if (first) {
            for (int idx = 1; idx < relstart; idx++) {
                ans += wordArraywithRoot[idx].LEMMA;
            }
        } else {
            int idx = 0;
            for (int i = findIdx(noun); i > 0; i--) {
                CoNLLWord c = wordArraywithRoot[i];
                if (c.DEPREL.equals("标点符号") && !c.LEMMA.equals("、") && !c.LEMMA.equals("(") && !c.LEMMA.equals(")")) {
                    idx = i;
                    break;
                }
            }
            for (idx++; idx < relstart; idx++) {
                ans += wordArraywithRoot[idx].LEMMA;
            }
        }
        return ans;
    }

    private static String findObject(CoNLLWord noun) {
        String ans = "";
        int idx = wordArraywithRoot.length;
        for (int i = findIdx(noun); i < idx; i++) {
            CoNLLWord c = wordArraywithRoot[i];
            if (c.DEPREL.equals("标点符号") && !c.LEMMA.equals("、") && !c.LEMMA.equals("(") && !c.LEMMA.equals(")")) {
                idx = i;
                break;
            }
        }
        for (int i = relend+1; i < idx; i++) {
            ans += wordArraywithRoot[i].LEMMA;
        }
        return ans;
    }

    private static int findIdx(CoNLLWord tar) {
        int ans = -1;
        for (int i = 0; i < wordArraywithRoot.length; i++) {
            if (wordArraywithRoot[i] == tar) {
                ans = i;
                break;
            }
        }
        return ans;
    }

    public static void docSyntacticParsing(String curString) throws Exception
    {
        // 依存句法分析
        parser = new NeuralNetworkDependencyParser();
        initalSentence = parser.parse(curString);
        wordArraywithRoot = initalSentence.getWordArrayWithRoot();

        // 特殊句式参数
        CoNLLWord neg = null;
        CoNLLWord pas = null;

        // 以CoNLLWord为中心的关系遍历
        for (int i = 0; i < wordArraywithRoot.length;i++) {
            // 当前依存关系
            CoNLLWord word = wordArraywithRoot[i];
            CoNLLWord hword = word.HEAD;

            // 确保不为空
            if (word==null || hword==null) {
                continue;
            }

            // 不要标点符号
//            if (word.POSTAG.equals("w") || hword.POSTAG.equals("w"))
//                continue;

            // 对当前word即宾语的限制,过滤形素词
            if (word.POSTAG.contains("A") || word.POSTAG.contains("g"))
                continue;

            // 输出当前的依存关系
            System.out.printf("%s[%s] --(%s)--> %s[%s]\n", word.LEMMA, word.POSTAG, word.DEPREL, hword.LEMMA, hword.POSTAG);

            // 实体-关系抽取
            if (word.DEPREL.equals("动宾关系")) {
                if (fin1 == false) {
                    fin1 = true;

                    // relation:谓语动词
                    relstart = findIdx(hword);
                    relend = relstart;
                    sup = findVerbPreSupplement(hword);
                    String t = sup + hword.LEMMA;
                    sup = findVerbPostSupplement(hword);
                    t += sup;
                    rel = t;

                    // source:主谓关系
                    CoNLLWord tmpn = initalSentence.findOneChildren(hword, "主谓关系");
                    if (tmpn == null)
                        continue;
                    csource = tmpn;
                    source = findSubject(tmpn, true);

                    // des:动宾关系
                    des = findObject(word);

                    // 抽取三元组
                    if (!source.isEmpty() && !rel.isEmpty() && !des.isEmpty()) {
                        triplenum++;
                        System.out.printf("triple[%d]( : %s : )\n", triplenum, rel);
//                        System.out.printf("triple[%d](%s : %s : %s)\n", triplenum, source, rel, des);
                    }

                } else {
                    // relation:谓语动词
                    relstart = findIdx(hword);
                    relend = relstart;
                    sup = findVerbPreSupplement(hword);
                    String t = sup + hword.LEMMA;
                    sup = findVerbPostSupplement(hword);
                    t += sup;
                    rel = t;

                    // source:如果同之前的关系不一样，要重新提取
                    CoNLLWord tmpn = initalSentence.findOneChildren(hword, "主谓关系");
                    if (tmpn == null)
                        tmpn = csource;
                    if (tmpn != csource) {
                        source = findSubject(tmpn, false);
                        csource = tmpn;
                    }

                    // des:动宾关系
                    des = findObject(word);

                    // 抽取三元组
                    if (!source.isEmpty() && !rel.isEmpty() && !des.isEmpty()) {
                        triplenum++;
//                        System.out.printf("triple[%d](%s : %s : %s)\n", triplenum, source, rel, des);
                        System.out.printf("triple[%d]( : %s : )\n", triplenum, rel);
                    }
                }

            }
        }
    }

    public static void main(String[] args) throws Exception {
//        String url ="http://sharepoint/Pressroom/Lists/Announcements/DispForm.aspx?ID=881&Source=http%3A%2F%2Fsharepoint%2FPressroom%2FLists%2FAnnouncements%2FAllItems%2Easpx";
//        String content = Html2Doc.Get_Content(url);
        initialParameters(s1);
        for (int i = 0; i < sentenceInput.length; i++) {
            System.out.printf("---------------------------No.%d---------------------------\n",i);
            curSentence = sentenceInput[i];
            // 句式处理
            rel = "";
            source = "";
            csource = null;
            des = "";
            sup = "";
            fin1 = false;
            // 去除“图片描述”，规整句式
            if (curSentence.contains("图1") || curSentence.contains("图2") || curSentence.contains("图3") || curSentence.contains("图4") || curSentence.contains("图5")) {
                int start = curSentence.indexOf("图");
                while(curSentence.charAt(start) != ' ') {
                    start ++;
                }
                curSentence = curSentence.substring(start);
            }
            curSentence = curSentence.trim();
            System.out.println(curSentence);
            docSyntacticParsing(curSentence);
        }
    }
}
