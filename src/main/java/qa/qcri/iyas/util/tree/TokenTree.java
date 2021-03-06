/**
 * Copyright 2017 Massimo Nicosia
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *  
 */
 
 
package qa.qcri.iyas.util.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.maltparser.core.helper.HashMap;

import qa.qcri.iyas.util.tree.node.BaseRichNode;
import qa.qcri.iyas.util.tree.node.RichTokenNode;

/**
 * 
 * TokenTree is a class which extends BaseRichNode and provides handy access to
 * a list of RichTokenNode
 * 
 * TokenTree object are used as Root node for several trees produced by the
 * framework
 * 
 * For example, POS+CHUNK roots are TokenTree objects
 */
public class TokenTree extends BaseRichNode {

	private List<RichTokenNode> tokens;

	private Map<String, RichTokenNode> idToToken;

	private int nextFreeId = 0;

	public TokenTree() {
		this.tokens = new ArrayList<>();
		this.idToToken = new HashMap<>();
	}

	/**
	 * 
	 * @return the list of RichTokenNode which ideally are descendants of this node
	 */
	public List<RichTokenNode> getTokens() {
		return this.tokens;
	}

	/**
	 * Adds a token to the list of tokens
	 * 
	 * @param token
	 * 
	 * @return the object instance for chaining
	 */
	public TokenTree addToken(RichTokenNode token) {
		this.tokens.add(token);
		this.idToToken.put(String.valueOf(nextFreeId++), token);
		return this;
	}

	/**
	 * Internally the nodes are associated with ids
	 * 
	 * This feature may be removed
	 * 
	 * @param id
	 * @return the RichTokenNode
	 */
	public RichTokenNode getTokenById(String id) {
		assert (this.idToToken.containsKey(id) == true);

		return this.idToToken.get(id);
	}

}
