package com.lc.nlp4han.dependency.tb;

import java.io.IOException;

import com.lc.nlp4han.dependency.DependencyParseEvaluateMonitor;
import com.lc.nlp4han.dependency.DependencyParseMeasure;
import com.lc.nlp4han.dependency.DependencySample;
import com.lc.nlp4han.ml.util.CrossValidationPartitioner;
import com.lc.nlp4han.ml.util.ModelWrapper;
import com.lc.nlp4han.ml.util.ObjectStream;
import com.lc.nlp4han.ml.util.TrainingParameters;

/**
 * @author 王宁
 * @version 创建时间：2018年7月25日 上午12:11:30 交叉验证
 */
public class DependencyParseCrossValidator
{
	private final TrainingParameters params;
	private DependencyParseEvaluateMonitor[] listeners;
	private DependencyParseMeasure measure = new DependencyParseMeasure();

	/**
	 * 构造
	 * 
	 * @param params
	 *            训练的参数
	 * @param listeners
	 *            监听器
	 */
	public DependencyParseCrossValidator(TrainingParameters params, DependencyParseEvaluateMonitor... listeners)
	{
		this.params = params;
		this.listeners = listeners;
	}

	/**
	 * 交叉验证
	 * 
	 * @param sample
	 *            样本流
	 * @param nFolds
	 *            折数
	 * @param contextGenerator
	 *            上下文
	 * 
	 * @throws IOException
	 *             io异常
	 */
	public void evaluate(ObjectStream<DependencySample> sample, int nFolds,
			DependencyParseContextGenerator contextGenerator) throws IOException
	{
		CrossValidationPartitioner<DependencySample> partitioner = new CrossValidationPartitioner<DependencySample>(
				sample, nFolds);
		int run = 1;
		while (partitioner.hasNext())
		{

			System.out.println("Run" + run + "...");

			// 训练模型
			CrossValidationPartitioner.TrainingSampleStream<DependencySample> trainingSampleStream = partitioner.next();
			ModelWrapper model = DependencyParserME.train(trainingSampleStream, params, contextGenerator);

			// 评价模型
			DependencyParseTBEvaluator evaluator = new DependencyParseTBEvaluator(
					new DependencyParserME(model, contextGenerator), listeners);
			evaluator.setMeasure(measure);
			evaluator.evaluate(trainingSampleStream.getTestSampleStream());

			System.out.println(measure);
			run++;
		}

		System.out.println(measure);
	}
}