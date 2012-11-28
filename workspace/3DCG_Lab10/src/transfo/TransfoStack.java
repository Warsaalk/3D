package transfo;

import java.util.Stack;

public class TransfoStack {

	private Stack<Transfo> stack;
	
	public TransfoStack(){
		stack = new Stack<Transfo>();
		stack.push(new Transfo());
	}
	
	public Transfo peek(){
		return stack.peek();
	}
	
	public void pop(){
		stack.pop();
	}
	
	public void push(){
		stack.push( new Transfo(this.peek()) );
	}
	
	public void transform(Transfo transfo){
		Transfo temp = this.peek();
		this.pop();
		temp.transform(transfo);
		stack.push(temp);
	}
	
}
