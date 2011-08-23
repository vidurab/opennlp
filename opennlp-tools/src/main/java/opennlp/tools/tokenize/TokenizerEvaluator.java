/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreemnets.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package opennlp.tools.tokenize;

import java.util.Collections;
import java.util.List;

import opennlp.tools.util.Span;
import opennlp.tools.util.eval.EvaluationMonitor;
import opennlp.tools.util.eval.Evaluator;
import opennlp.tools.util.eval.FMeasure;

/**
 * The {@link TokenizerEvaluator} measures the performance of
 * the given {@link Tokenizer} with the provided reference
 * {@link TokenSample}s.
 *
 * @see Evaluator
 * @see Tokenizer
 * @see TokenSample
 */
public class TokenizerEvaluator extends Evaluator<TokenSample> {

  private FMeasure fmeasure = new FMeasure();
  
  /**
   * The {@link Tokenizer} used to create the
   * predicted tokens.
   */
  private Tokenizer tokenizer;

  /**
   * Initializes the current instance with the
   * given {@link Tokenizer}.
   *
   * @param tokenizer the {@link Tokenizer} to evaluate.
   */
  public TokenizerEvaluator(Tokenizer tokenizer) {
    this.tokenizer = tokenizer;
  }
  
  /**
   * Initializes the current instance with the
   * given {@link Tokenizer}.
   *
   * @param tokenizer the {@link Tokenizer} to evaluate.
   * @param listeners evaluation sample listeners
   */
  public TokenizerEvaluator(Tokenizer tokenizer, List<? extends EvaluationMonitor<TokenSample>> listeners) {
    super(listeners);
    this.tokenizer = tokenizer;
  }
  
  /**
   * Initializes the current instance with the given {@link Tokenizer}.
   * 
   * @param tokenizer
   *          the {@link Tokenizer} to evaluate.
   * @param listener
   *          evaluation sample listener
   */
  public TokenizerEvaluator(Tokenizer tokenizer,
      EvaluationMonitor<TokenSample> listener) {
    this(tokenizer, Collections.singletonList(listener));
 }

  @Override
  protected TokenSample processSample(TokenSample reference) {
    Span predictions[] = tokenizer.tokenizePos(reference.getText());
    fmeasure.updateScores(reference.getTokenSpans(), predictions);
    
    return new TokenSample(reference.getText(), predictions);
  }
  
  public FMeasure getFMeasure() {
    return fmeasure;
  }
}