package org.nlp4han.coref.centering;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.nlp4han.coref.hobbs.AttributeFilter;
import org.nlp4han.coref.hobbs.AttributeGeneratorByDic;
import org.nlp4han.coref.hobbs.EvaluationHobbs;
import org.nlp4han.coref.hobbs.Hobbs;
import org.nlp4han.coref.hobbs.NodeNameFilter;
import org.nlp4han.coref.hobbs.PNFilter;

import com.lc.nlp4han.constituent.BracketExpUtil;
import com.lc.nlp4han.constituent.TreeNode;
import com.lc.nlp4han.constituent.TreeNodeUtil;

public class TestCenteringBFP
{
	@Test
	public void testResolve_1()
	{
		String str;
		str = "((IP(NP(PN 我))(VP(PP(P在)(LCP(NP(DNP(NP(NP(NR 太平洋))(NP(NN 酒店)))(DEG 的))(NP(NN 咖啡厅)))(LC 里)))(VP(VV 看见)(AS 了)(NP(NR 庞德))))(PU 。)))";
		TreeNode s1 = BracketExpUtil.generateTreeNoTopBracket(str);
		str = "((IP(IP(NP(NP(PN 他))(CC 和)(NP(QP(CD 一)(CLP(M 个)))(NP(NN 姑娘))))(VP(VV 坐)(ADVP(AD 在一起))))(PU 。)))";
		TreeNode s2 = BracketExpUtil.generateTreeNoTopBracket(str);

		List<TreeNode> ss = new ArrayList<TreeNode>();
		ss.add(s1);
		ss.add(s2);

		CenteringBFP bfp = new CenteringBFP();
		bfp.setGrammaticalRoleRuleSet(GrammaticalRoleRuleSet.getGrammaticalRoleRuleSet());		//设置语法角色规则集，此规则集为缺省值
		
		AttributeFilter attributeFilter = new AttributeFilter(new PNFilter()); // 组合过滤器
		attributeFilter.setAttributeGenerator(new AttributeGeneratorByDic()); // 装入属性生成器
		bfp.setAttributeFilter(attributeFilter);	//设置属性过滤器，此过滤器为缺省值
		
		Map<TreeNode, TreeNode> result = bfp.resolve(ss);
		
		Map<TreeNode, TreeNode> goal = new HashMap<TreeNode, TreeNode>();
		goal.put(TreeNodeUtil.getAllLeafNodes(s2).get(0), TreeNodeUtil.getAllLeafNodes(s1).get(9));

		assertEquals(goal, result);

	}

	@Test
	public void testResolve_2()
	{
		String str;
		// 句一：小明的妈妈是一名教师。
		str = "((IP(NP(DNP(NP(NN 小明))(DEG 的))(NP(NN 妈妈)))(VP(VC 是)(NP(QP(CD 一)(CLP(M 名)))(NP(NN 教师))))(PU 。)))";
		TreeNode s1 = BracketExpUtil.generateTreeNoTopBracket(str);
		// 句二：家长们都很喜欢她。
		str = "((IP(IP(NP(NN 家长们))(VP(ADVP(AD 都))(ADVP(AD 很))(VP(VV 喜欢)(NP(PN 她)))))(PU 。)))";
		TreeNode s2 = BracketExpUtil.generateTreeNoTopBracket(str);

		List<TreeNode> ss = new ArrayList<TreeNode>();
		ss.add(s1);
		ss.add(s2);

		CenteringBFP bfp = new CenteringBFP();
		bfp.setGrammaticalRoleRuleSet(GrammaticalRoleRuleSet.getGrammaticalRoleRuleSet());		//设置语法角色规则集，此规则集为缺省值
		
		AttributeFilter attributeFilter = new AttributeFilter(new PNFilter()); // 组合过滤器
		attributeFilter.setAttributeGenerator(new AttributeGeneratorByDic()); // 装入属性生成器
		bfp.setAttributeFilter(attributeFilter);	//设置属性过滤器，此过滤器为缺省值

		Map<TreeNode, TreeNode> result = bfp.resolve(ss);

		Map<TreeNode, TreeNode> goal = new HashMap<TreeNode, TreeNode>();
		goal.put(TreeNodeUtil.getAllLeafNodes(s2).get(4), TreeNodeUtil.getAllLeafNodes(s1).get(2));

		assertNotEquals(goal, result);
	}
	
	@Test
	public void testResolve_3()
	{
		String str;
		// 句一：约翰看中了一辆汽车。
		str = "((IP(NP(NR 小明))(VP(VV 看中)(AS 了)(NP(QP(CD 一)(CLP(M 辆)))(NP(NN 汽车))))(PU 。)))";
		TreeNode s1 = BracketExpUtil.generateTreeNoTopBracket(str);
		// 句二：他向杰克展示了它。
		str = "((IP(NP(PN 他))(VP(PP(P 向)(NP(NR 小红)))(VP(VV 展示)(AS 了)(NP(PN 它))))(PU 。)))";
		TreeNode s2 = BracketExpUtil.generateTreeNoTopBracket(str);
		// 句二：他决定买下它。
		str = "((IP(NP(PN 他))(VP(VV 决定)(IP(VP(VV 买下)(NP(PN 它)))))))";
		TreeNode s3 = BracketExpUtil.generateTreeNoTopBracket(str);
		
		List<TreeNode> ss = new ArrayList<TreeNode>();
		ss.add(s1);
		ss.add(s2);
		ss.add(s3);
		
		CenteringBFP bfp = new CenteringBFP();
		bfp.setGrammaticalRoleRuleSet(GrammaticalRoleRuleSet.getGrammaticalRoleRuleSet());		//设置语法角色规则集，此规则集为缺省值
		
		AttributeFilter attributeFilter = new AttributeFilter(new PNFilter()); // 组合过滤器
		attributeFilter.setAttributeGenerator(new AttributeGeneratorByDic()); // 装入属性生成器
		bfp.setAttributeFilter(attributeFilter);	//设置属性过滤器，此过滤器为缺省值
		
		Map<TreeNode, TreeNode> result = bfp.resolve(ss);
		
		Map<TreeNode, TreeNode> goal = new HashMap<TreeNode, TreeNode>();
		goal.put(TreeNodeUtil.getAllLeafNodes(s2).get(0), TreeNodeUtil.getAllLeafNodes(s1).get(0));
		goal.put(TreeNodeUtil.getAllLeafNodes(s2).get(5), TreeNodeUtil.getAllLeafNodes(s1).get(5));
		goal.put(TreeNodeUtil.getAllLeafNodes(s3).get(0), TreeNodeUtil.getAllLeafNodes(s1).get(0));
		goal.put(TreeNodeUtil.getAllLeafNodes(s3).get(3), TreeNodeUtil.getAllLeafNodes(s1).get(5));
		
		assertEquals(goal, result);
	}
	
	@Test
	public void testResolve_4()
	{
		String str;
		// 句一：约翰看中了一辆汽车。
		str = "((IP(NP(NR 小明))(VP(VV 看中)(AS 了)(NP(QP(CD 一)(CLP(M 辆)))(NP(NN 汽车))))(PU 。)))";
		TreeNode s1 = BracketExpUtil.generateTreeNoTopBracket(str);
		// 句二：他向杰克展示了它。
		str = "((IP(NP(PN 他))(VP(PP(P 向)(NP(NR 小红)))(VP(VV 展示)(AS 了)(NP(PN 它))))(PU 。)))";
		TreeNode s2 = BracketExpUtil.generateTreeNoTopBracket(str);
		// 句二：他决定买下它。
		str = "((IP(NP(PN 他))(VP(VV 决定)(IP(VP(VV 买下)(NP(PN 它)))))))";
		TreeNode s3 = BracketExpUtil.generateTreeNoTopBracket(str);
		
		List<TreeNode> ss = new ArrayList<TreeNode>();
		ss.add(s1);
		ss.add(s2);
		ss.add(s3);
		
		CenteringBFP bfp = new CenteringBFP();
		bfp.setGrammaticalRoleRuleSet(GrammaticalRoleRuleSet.getGrammaticalRoleRuleSet());		//设置语法角色规则集，此规则集为缺省值
		
		AttributeFilter attributeFilter = new AttributeFilter(new PNFilter()); // 组合过滤器
		attributeFilter.setAttributeGenerator(new AttributeGeneratorByDic()); // 装入属性生成器
		bfp.setAttributeFilter(attributeFilter);	//设置属性过滤器，此过滤器为缺省值
		
		TreeNode result = bfp.resolve(ss, TreeNodeUtil.getAllLeafNodes(s2).get(5));
		
		TreeNode goal = TreeNodeUtil.getAllLeafNodes(s1).get(5);
		
		assertEquals(goal, result);
	}

	@Test
	public void testGenerateCenter()
	{
		String str;
		// 句一：小明看中了一辆汽车。
		str = "((IP(NP(NR 小明))(VP(VV 看中)(AS 了)(NP(QP(CD 一)(CLP(M 辆)))(NP(NN 汽车))))(PU 。)))";
		TreeNode s1 = BracketExpUtil.generateTreeNoTopBracket(str);
		// 句二：他向小红展示了它。
		str = "((IP(NP(PN 他))(VP(PP(P 向)(NP(NR 小红)))(VP(VV 展示)(AS 了)(NP(PN 它))))(PU 。)))";
		TreeNode s2 = BracketExpUtil.generateTreeNoTopBracket(str);
		// 句二：他决定买下它。
		str = "((IP(NP(PN 他))(VP(VV 决定)(IP(VP(VV 买下)(NP(PN 它)))))))";
		TreeNode s3 = BracketExpUtil.generateTreeNoTopBracket(str);
		
		List<Entity> e1 = Entity.entities(s1, GrammaticalRoleRuleSet.getGrammaticalRoleRuleSet());
		List<Entity> e2 = Entity.entities(s2, GrammaticalRoleRuleSet.getGrammaticalRoleRuleSet());
		List<Entity> e3 = Entity.entities(s3, GrammaticalRoleRuleSet.getGrammaticalRoleRuleSet());
		
		Center c1 = new Center(e1, e1);
		CenteringBFP bfp = new CenteringBFP();
		bfp.setGrammaticalRoleRuleSet(GrammaticalRoleRuleSet.getGrammaticalRoleRuleSet());		//设置语法角色规则集，此规则集为缺省值
		
		AttributeFilter attributeFilter = new AttributeFilter(new PNFilter()); // 组合过滤器
		attributeFilter.setAttributeGenerator(new AttributeGeneratorByDic()); // 装入属性生成器
		bfp.setAttributeFilter(attributeFilter);	//设置属性过滤器，此过滤器为缺省值
		
		Center c2 = bfp.generateCenter(e2, e1, c1, s2, s1);
		Center c3 = bfp.generateCenter(e3, e2, c2, s3, s2);
		
		assertEquals("[Cb=小明, Cf=(小明, 小红, 汽车), Cp=小明]", c2.toString());
		assertEquals("[Cb=小明, Cf=(小明, 汽车), Cp=小明]", c3.toString());
	}
	
	@Test
	public void testGetTransition_1()
	{
		String str;
		// 句一：小明看中了一辆汽车。
		str = "((IP(NP(NR 小明))(VP(VV 看中)(AS 了)(NP(QP(CD 一)(CLP(M 辆)))(NP(NN 汽车))))(PU 。)))";
		TreeNode s1 = BracketExpUtil.generateTreeNoTopBracket(str);
		// 句二：他向小刚展示了它。
		str = "((IP(NP(PN 他))(VP(PP(P 向)(NP(NR 小刚)))(VP(VV 展示)(AS 了)(NP(PN 它))))(PU 。)))";
		TreeNode s2 = BracketExpUtil.generateTreeNoTopBracket(str);
		// 句二：他决定买下它。
		str = "((IP(NP(PN 他))(VP(VV 决定)(IP(VP(VV 买下)(NP(PN 它)))))))";
		TreeNode s3 = BracketExpUtil.generateTreeNoTopBracket(str);
		
		//e1:小明，汽车
		List<Entity> e1 = Entity.entities(s1, GrammaticalRoleRuleSet.getGrammaticalRoleRuleSet());
		
		//e2:他，小刚，它
		List<Entity> e2 = Entity.entities(s2, GrammaticalRoleRuleSet.getGrammaticalRoleRuleSet());
		
		//e3:他，它
		List<Entity> e3 = Entity.entities(s3, GrammaticalRoleRuleSet.getGrammaticalRoleRuleSet());
		
		Center c1 = new Center(e1, e1);
		
		List<Entity> newE2 = new ArrayList<Entity>();	//小明，小刚，汽车
		newE2.add(e1.get(0));
		newE2.add(e2.get(1));
		newE2.add(e1.get(1));
		Center c2 = new Center(e2, newE2);
		
		String transition1 = CenteringBFP.getTransition(c2, c1);
		
		List<Entity> newE3 = new ArrayList<Entity>();	//小刚，汽车
		newE3.add(newE2.get(1));
		newE3.add(newE2.get(2));
		Center c3 = new Center(e3, newE3);
		
		String transition2 = CenteringBFP.getTransition(c3, c2);
		
		
		assertEquals("Continue", transition1);
		assertEquals("Smooth-Shift", transition2);
		
	}
	
	@Test
	public void testGetTransition_2()
	{
		String str;
		// 句一：小明看中了一辆汽车。
		str = "((IP(NP(NR 小明))(VP(VV 看中)(AS 了)(NP(QP(CD 一)(CLP(M 辆)))(NP(NN 汽车))))(PU 。)))";
		TreeNode s1 = BracketExpUtil.generateTreeNoTopBracket(str);
		// 句二：他向小刚展示了它。
		str = "((IP(NP(PN 他))(VP(PP(P 向)(NP(NR 小刚)))(VP(VV 展示)(AS 了)(NP(PN 它))))(PU 。)))";
		TreeNode s2 = BracketExpUtil.generateTreeNoTopBracket(str);
		// 句二：他决定买下它。
		str = "((IP(NP(PN 他))(VP(VV 决定)(IP(VP(VV 买下)(NP(PN 它)))))))";
		TreeNode s3 = BracketExpUtil.generateTreeNoTopBracket(str);
		
		//e1:小明，汽车
		List<Entity> e1 = Entity.entities(s1, GrammaticalRoleRuleSet.getGrammaticalRoleRuleSet());
		
		//e2:他，小刚，它
		List<Entity> e2 = Entity.entities(s2, GrammaticalRoleRuleSet.getGrammaticalRoleRuleSet());
		
		//e3:他，它
		List<Entity> e3 = Entity.entities(s3, GrammaticalRoleRuleSet.getGrammaticalRoleRuleSet());
		
		Center c1 = new Center(e1, e1);
		
		List<Entity> newE2 = new ArrayList<Entity>();	//小明，小刚，汽车
		newE2.add(e1.get(0));
		newE2.add(e2.get(1));
		newE2.add(e1.get(1));
		Center c2 = new Center(e2, newE2);
		
		String transition1 = CenteringBFP.getTransition(c2, c1);
		
		List<Entity> newE3 = new ArrayList<Entity>();	//小刚，汽车
		newE3.add(newE2.get(1));
		newE3.add(newE2.get(2));
		Center c3 = new Center(e3, newE3);
		
		String transition2 = CenteringBFP.getTransition(c3, c2);
		
		
		assertEquals("Continue", transition1);
		assertEquals("Smooth-Shift", transition2);
	}
}
