package com.lxs;
import com.hankcs.hanlp.corpus.document.sentence.Sentence;
import com.hankcs.hanlp.corpus.document.sentence.word.IWord;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.model.perceptron.*;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLSentence;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLWord;
import com.hankcs.hanlp.dependency.IDependencyParser;
import com.hankcs.hanlp.dependency.nnparser.NeuralNetworkDependencyParser;
import com.hankcs.hanlp.seg.common.Term;

import java.util.*;

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
    public static String st = ("而售前的角度：恒天是一家神一样的单位，无所不能");

    // 句法结构基本参数
    private static IDependencyParser parser;
    private static CoNLLSentence initalSentence;
    private static String[] sentenceInput;
    private static int triplenum;
    private static Set<String> ChinesePassive = new HashSet<String>();
    private static Set<String> ChineseNegative = new HashSet<String>();

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
        sentenceInput = initialSentence.split("!|。|\n");
    }

    public static CoNLLSentence docNER(String curSentence)
    {
        // 依存句法分析器
        parser = new NeuralNetworkDependencyParser();
        initalSentence = parser.parse(curSentence);

        // 先进行NER,其他词性的词不变
        CoNLLWord[] wordArraywithRoot = initalSentence.getWordArrayWithRoot();
        List<CoNLLWord> middle = new ArrayList<CoNLLWord>();
        for (int i = 0; i < wordArraywithRoot.length; i++)
        {
            CoNLLWord word = wordArraywithRoot[i];
            CoNLLWord nword = word;
            if(word.POSTAG.contains("n") || word.POSTAG.contains("r") || word.POSTAG.contains("nr") || word.POSTAG.contains("ns") || word.POSTAG.contains("q") || word.POSTAG.contains("nt") ||word.POSTAG.contains("m")|| word.POSTAG.contains("nz") || word.POSTAG.contains("t") || word.POSTAG.contains("j") || word.POSTAG.contains("N")) {
                if (word.VISIT == 1) {
                    continue;
                }

                // 以该名词为核心构建名词
                String tar = "";
                List<CoNLLWord> sup1 = initalSentence.findChildren(word);
                for (int k = 0;k < sup1.size(); k++) {
                    CoNLLWord next = sup1.get(k);
                    if (next.VISIT == 1) {
                        continue;
                    }
                    next.VISIT = 1;
                    CoNLLWord child = initalSentence.findOneChildren(next, "定中关系");
                    while(child != null) {
                        if (child.VISIT == 1) {
                            child = initalSentence.findOneChildren(child, "定中关系");
                            continue;
                        }
                        tar += child.LEMMA;
                        child.VISIT = 1;
                        child = initalSentence.findOneChildren(child, "定中关系");
                    }
                    if (next.DEPREL.equals("右附加关系")) {
                        word.LEMMA += next.LEMMA;
                    } else {
                        tar += sup1.get(k).LEMMA;
                    }
                }

                if (tar != "") {
                    tar += word.LEMMA;
                    nword.LEMMA = tar;
                    nword.LEMMA.replace("\n"," ");
                    nword.NAME = nword.LEMMA;
                    nword.VISIT = 0;
                    nword.POSTAG = "n";
                }
            }
            middle.add(nword);
        }

        List<Term> middleX = new ArrayList<Term>();
        for (int i = 0; i < middle.size(); i++) {
            CoNLLWord cur = middle.get(i);
            Term term = new Term(cur.LEMMA, Nature.create(cur.POSTAG));
            if (cur.VISIT == 0)
                middleX.add(term);
        }

        // NER之后重新句法分析
        CoNLLSentence entitySentence = parser.parse(middleX);

//        for (int i = entitySentence.word.length-1; i >= 0;i--) {
//            CoNLLWord word = entitySentence.word[i];
//            CoNLLWord hword = word.HEAD;
//            System.out.printf("%s[%s] --(%s)--> %s[%s]\n", word.LEMMA, word.POSTAG, word.DEPREL, hword.LEMMA, hword.POSTAG);
//        }
//
//        // 输出CoNLL格式的表格
        System.out.println(entitySentence);
        return entitySentence;
    }

    private static String findVerbSupplement(CoNLLWord verb) {
        List<CoNLLWord> sup1 = initalSentence.findChildren(verb, "状中关系");
        String ans = "";
        for (int i = 0;i < sup1.size(); i++) {
            CoNLLWord curw = sup1.get(i);
            List<String> sup2 = new ArrayList<String>();
            CoNLLWord child = initalSentence.findOneChildren(curw, "状中关系");
            while(child != null && (child.POSTAG.contains("v")||child.POSTAG.contains("c"))) {
                sup2.add(child.LEMMA);
                child = initalSentence.findOneChildren(child, "状中关系");
            }
            for (int j = 0; j < sup2.size(); j--) {
                ans += sup2.get(j);
            }
            ans += sup1.get(i).LEMMA;
        }
        return ans;
    }

    private static CoNLLWord findDes(CoNLLWord verb) {
        CoNLLWord child = initalSentence.findOneChildren(verb, "动宾关系");
        while(child != null && (!(child.POSTAG.contains("n")||child.POSTAG.contains("r"))) ) {
            child = initalSentence.findOneChildren(child, "动宾关系");
        }
        return child;
    }

    public static void docSyntacticParsing(String curString) throws Exception
    {
        // 首先进行NER
        CoNLLSentence entitySentence = docNER(curString);
        CoNLLWord[] wordArraywithRoot = entitySentence.getWordArrayWithRoot();

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
            if (word.POSTAG.equals("w") || hword.POSTAG.equals("w")) {
                continue;
            }

            // 当前可能组合的三元组
            String rel;
            CoNLLWord source;
            CoNLLWord des;
            String sup;

            // 输出当前的依存关系
//            System.out.printf("%s[%s] --(%s)--> %s[%s]\n", word.LEMMA, word.POSTAG, word.DEPREL, hword.LEMMA, hword.POSTAG);
            // 基本关系
            if (word.DEPREL.equals("主谓关系")) {
                // relation:谓语动词
                sup = findVerbSupplement(hword);
                hword.LEMMA = sup + hword.LEMMA;
                rel = hword.LEMMA;
                // source:主谓关系
                source = initalSentence.findOneChildren(hword, "主谓关系");
                // des:动宾关系
                des = findDes(hword);
//                sup = findNounSupplement(des);
//                if (des != null) {
//                    des.LEMMA = sup + des.LEMMA;
//                }

                if (source != null && des != null) {
                    triplenum++;
                    System.out.printf("triple[%d](%s : %s : %s)\n", triplenum, source.LEMMA, rel, des.LEMMA);
                }
            }

            // 特殊句式：否定关系
            if (word.DEPREL.equals("主谓关系") && (hword.POSTAG.equals("c") || hword.POSTAG.equals("d"))) {
                neg = hword;
                // relation:谓语动词
                rel = hword.LEMMA;
                if (pas != null) {
                    rel += "被";
                    rel += pas.LEMMA;
                }
                // source:主谓关系
                source = initalSentence.findOneChildren(hword, "主谓关系");
                // des:动宾关系
                if (pas != null) {
                    hword = pas;
                    des = findDes(hword);
                    if (des == null) {
                        CoNLLWord other2 = initalSentence.findOneChildren(hword, "并列关系");
                        des = findDes(other2);
                    }
                }
                else {
                    des = findDes(hword);
//                    sup = findNounSupplement(des);
//                    if (des != null)
//                        des.LEMMA = sup + des.LEMMA;
                }

                if (source != null && des != null) {
                    triplenum++;
                    System.out.printf("triple[%d](%s : %s : %s)\n", triplenum, source.LEMMA, rel, des.LEMMA);
                }
            }

            // 特殊句式：被动关系
            if (hword.POSTAG.contains("v") && word.DEPREL.equals("状中结构") && word.POSTAG.equals("p")) {
                pas = hword;
                // relation:被+动词
                rel = word.LEMMA+hword.LEMMA;
                CoNLLWord child = initalSentence.findOneChildren(hword, "动宾关系");
                if (child != null) {
                    rel += child.LEMMA;
                }
                List<CoNLLWord> others = initalSentence.findChildren(hword, "并列关系");
                for (int k = 0;k < others.size(); k++) {
                    rel += others.get(k).LEMMA;
                }
                if (neg != null) {
                    rel = neg.LEMMA + rel;
                }
                // source:前置宾语
                source = initalSentence.findOneChildren(hword, "前置宾语");
                if (neg != null) {
                    source = initalSentence.findOneChildren(neg, "主谓关系");
                }

                // des:动宾关系
                des = findDes(hword);
                if (des == null) {
                    CoNLLWord other2 = initalSentence.findOneChildren(hword, "并列关系");
                    des = findDes(other2);
//                    sup = findNounSupplement(des);
//                    if (des != null) {
//                        des.LEMMA = sup + des.LEMMA;
//                    }
                }

                if (source != null && des != null) {
                    triplenum++;
                    System.out.printf("triple[%d](%s : %s : %s)\n", triplenum, source.LEMMA, rel, des.LEMMA);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        initialParameters(st);
        for (int i = 0; i < sentenceInput.length; i++) {
            System.out.printf("---------------------------No.%d---------------------------\n",i);
            String curSentence = sentenceInput[i];
            System.out.println(curSentence);
            docSyntacticParsing(curSentence);
        }
    }
}
