package com.lc.nlp4han.constituent.unlex;

import java.util.LinkedList;

/**
 * @author 王宁
 * @version 创建时间：2018年9月24日 下午6:41:59 二元规则
 */
public class BinaryRule extends Rule
{
	private short leftChild;
	private short rightChild;
	LinkedList<LinkedList<LinkedList<Double>>> scores = new LinkedList<LinkedList<LinkedList<Double>>>() ;// 保存规则例如A -> BC 的概率

	public BinaryRule(short parent, short lChild, short rChild)
	{
		super.parent = parent;
		this.leftChild = lChild;
		this.rightChild = rChild;
	}

	
	
	@Override
	public void split()
	{
	}



	public boolean isSameRule(short parent, short lChild, short rChild)
	{
		if (this.parent == parent && this.leftChild == lChild && this.rightChild == rChild)
			return true;
		else
			return false;
	}

	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + leftChild;
		result = prime * result + rightChild;
		return result;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BinaryRule other = (BinaryRule) obj;
		if (leftChild != other.leftChild)
			return false;
		if (rightChild != other.rightChild)
			return false;
		return true;
	}


	public short getLeftChild()
	{
		return leftChild;
	}


	public void setLeftChild(short leftChild)
	{
		this.leftChild = leftChild;
	}


	public short getRightChild()
	{
		return rightChild;
	}


	public void setRightChild(short rightChild)
	{
		this.rightChild = rightChild;
	}


	public LinkedList<LinkedList<LinkedList<Double>>> getScores()
	{
		return scores;
	}


	public void setScores(LinkedList<LinkedList<LinkedList<Double>>> scores)
	{
		this.scores = scores;
	}

}
