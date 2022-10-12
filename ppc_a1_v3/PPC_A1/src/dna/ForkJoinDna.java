package dna;

import java.util.List;
import java.util.concurrent.RecursiveTask;

import dna.problems.Problem;

public class ForkJoinDna extends RecursiveTask<int[]> {

	private static final long serialVersionUID = 1L;
	private Problem p;
	private char[] searchSeq;
	private int start;
	private int end;
	private List<String> patterns;
	
	public ForkJoinDna(Problem p,  int start, int end) {
		this.searchSeq = p.getSearchSequence();
		this.patterns = p.getPatterns();
		this.start = start;
		this.end = end;
	}

	@Override
	protected int[] compute() {
		
		if ((end-start) < 0) return patternIdentifier();
		
		ForkJoinDna fjd1 = new ForkJoinDna(p,start,(end-start)/2);
		
		ForkJoinDna fjd2 = new ForkJoinDna(p,start+1+(end-start)/2,end);
		
		fjd1.compute();
		fjd2.compute();		
		
		
		
	}
	
	private int[] patternIdentifier() {		
		
		int[] results = new int[p.getPatterns().size()]; 
		
		for(int i=0;i<searchSeq.length;i++) {
			for(int j=0;j<p.getPatterns().size();j++) {
				if(Benchmark.isIn(searchSeq,i,p.getPatterns().get(j)))
					results[j]++;
			}
		}

		return results;	
	}
	

}
