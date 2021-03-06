package com.lc.nlp4han.dependency.tb;

import com.lc.nlp4han.dependency.DependencyParseEvaluateMonitor;
import com.lc.nlp4han.dependency.DependencyParseMeasure;
import com.lc.nlp4han.dependency.DependencyParser;
import com.lc.nlp4han.dependency.DependencySample;
import com.lc.nlp4han.ml.util.Evaluator;


public class DependencyParseEvaluator extends Evaluator<DependencySample>
{
	private DependencyParser tagger;
	private DependencyParseMeasure measure;

	public DependencyParseEvaluator(DependencyParser tagger, DependencyParseEvaluateMonitor... monitor)
	{
		super(monitor);
		this.tagger = tagger;
	}

	@Override
	protected DependencySample processSample(DependencySample refSample)
	{
		String[] wordsRef = refSample.getWords();
		String[] posRef = refSample.getPos();
		String[] dependencyRef = refSample.getDependency();
		String[] dependencyWordsRef = refSample.getDependencyWords();

		String[] wordsRefNoRoot = new String[wordsRef.length - 1];
		String[] posRefNoRoot = new String[posRef.length - 1];
		for (int i = 1; i < wordsRef.length; i++)
		{
			wordsRefNoRoot[i - 1] = wordsRef[i];
			posRefNoRoot[i - 1] = posRef[i];
		}
	
		DependencySample preSample = tagger.parse(wordsRefNoRoot, posRefNoRoot,1)[0].getSample();
//		// 将预测的结果输出
//		System.out.println(preSample.toCoNLLString());

		String[] dependencyWordsPre = preSample.getDependencyWords();
		String[] dependencyPre = preSample.getDependency();

		measure.updateScore(dependencyWordsRef, dependencyRef, dependencyWordsPre, dependencyPre);

		return preSample;
	}

	public DependencyParseMeasure getMeasure()
	{
		return measure;
	}

	public void setMeasure(DependencyParseMeasure measure)
	{
		this.measure = measure;
	}

}
