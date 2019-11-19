package com.lxs;
import com.hankcs.hanlp.corpus.document.sentence.Sentence;
import com.hankcs.hanlp.model.perceptron.*;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLSentence;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLWord;
import com.hankcs.hanlp.dependency.IDependencyParser;
import com.hankcs.hanlp.dependency.nnparser.NeuralNetworkDependencyParser;

public class Test {

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
    public static String s2 = (
        "2019年10月16日由PMO组织的2019年第四期项目管理分享会最终圆满举行，此次session邀请到了售前经理唐修富一起聊聊售前部门的主要工作、分享在与客户接触过程中的沟通小故事以及对客户需求的挖掘；修富以一个比较轻松的方式-小视频带大家进入了此次分享会，视频生动形象的反映了甲方乙方的故事，整个现场人生人海！\n"
        +"分享会分为3个篇章，第一篇是乙方的宿命；恒天是一家什么公司？有朋友说就是外包公司。而售前的角度：恒天是一家神一样的单位，无所不能。恒天是一家技术公司，世界500强的企业都用我们的技术。\n"
        +"第二篇是售前故事，售前的工作内容主要涉及1、项目&产品支持2、线索分析3、售前培训4、信息分析5、日常工作6、附属工作，希望通过售前和各位同事的努力之后，将公司对外标书的制定能力以及达标的能力进一步提升。\n"
        +"第三篇是甲方的心思，涉及到1、与人沟通的开场白2、需求潜排序3、需求动机挖掘4、在谈判过程中，有顾问专家在场，要安抚、赞扬5、三天成为谈判专家：自身形象；沉稳地谈吐；了解这个行业的业务领域及技术线；了解这个的优势和缺点（缺点更重要）；6、与客户做朋友，交到朋友，带来持续的合作；7、完美的陷阱，向客户介绍时将客户不太关注的缺点，不能说这个方案是完美无瑕，客户心里预防是越完美就越有顾虑。8、给客户讲案例故事：建议按照案例的方式与客户去讲故事，可以看客户的反应，用了我们的方案的改善情况；标准产品及定制项目的优势，没有使用我们方案的客户；不要一个劲讲自己的产品，以免产生阻隔。"
    );

    public static void testNER() throws Exception
    {
        // 融合了分词、pos和NER的词法分析器
        PerceptronNERecognizer recognizer = new PerceptronNERecognizer(Config.NER_MODEL_FILE);
        PerceptronLexicalAnalyzer analyzer = new PerceptronLexicalAnalyzer(new PerceptronSegmenter(), new PerceptronPOSTagger(), recognizer);
        Sentence result = analyzer.analyze(s1);
        System.out.println(result.toStringOnlyNoun());
    }

    public static void extractTriple(CoNLLSentence sen) {
        // 主谓宾关系：刘小绪生于四川
        // dic是这个词语的依存句法字典
//        if (dic.containsKey("主谓关系") && dic.containsKey("动宾关系")){
//
//            // 当前的词语，用上面的例子来说，relation=“生于”
//            String relation = CoNLLWord.LEMMA;
//
//            // 用循环遍历，是因为关系列表里面不一定只有一个词语
//            for (CoNLLWord entity1:
//                dic.get("主谓关系")) {
//
//                for (CoNLLWord entity2:
//                    dic.get("动宾关系")) {
//
//                    System.out.println(entity1.LEMMA + "," + relation + "," + entity2.LEMMA);
//                }
//
//            }
//        }
    }

    public static void testSyntacticParsing() throws Exception
    {
        // 句法分析器：基于NN
        IDependencyParser parser = new NeuralNetworkDependencyParser();
        CoNLLSentence sentence = parser.parse("比尔盖茨是微软的创始人，他在1990年创建了微软公司");
//        CoNLLSentence sentence = parser.parse("我今天跟他打了一架");
//        CoNLLSentence sentence = parser.parse("我是他的敌人");
        System.out.println(sentence);

        // 也可以直接拿到数组，任意顺序或逆序遍历
        CoNLLWord[] wordArray = sentence.getWordArray();
        for (int i = wordArray.length - 1; i >= 0; i--)
        {
            CoNLLWord word = wordArray[i];
            System.out.printf("%s --(%s)--> %s\n", word.LEMMA, word.DEPREL, word.HEAD.LEMMA);
        }

        // 基于依存句法分析抽取三元组
        extractTriple(sentence);

        // 还可以直接遍历子树，从某棵子树的某个节点一路遍历到虚根
//        CoNLLWord head = wordArray[12];
//        while ((head = head.HEAD) != null)
//        {
//            if (head == CoNLLWord.ROOT) System.out.println(head.LEMMA);
//            else System.out.printf("%s --(%s)--> ", head.LEMMA, head.DEPREL);
//        }
    }

    public static void main(String[] args) throws Exception {
        testNER();
//        testSyntacticParsing();
    }
}
