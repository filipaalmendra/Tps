package dna;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BiFunction;

import dna.problems.P1Small;
import dna.problems.P2Large;
import dna.problems.Problem;

public class Benchmark {


	public static Result sequential(Problem p, Integer ncores) {
		
		char[] ss = p.getSearchSequence();
		List<String> gp = p.getPatterns();
		
		
		int[] results = new int[gp.size()]; 
		
		for(int i=0;i<ss.length;i++) {
			for(int j=0;j<gp.size();j++) {
				if(isIn(ss,i,gp.get(j)))
					results[j]++;
			}
		}

		return new Result(results);
	}
	
	public static Result parallel(Problem p, Integer ncores) {
		
		int[] results = new int[p.getPatterns().size()];
		
		ForkJoinDna bm = new ForkJoinDna(p, 0,p.getSearchSequence().length);
		ForkJoinPool pool = new ForkJoinPool(ncores);
		pool.invoke(bm);
		results = bm.join();
		
		return new Result(results);
	}
	
	
	
	public static boolean isIn(char[] arr, int start, String pattern) {
		if ( (arr.length - start) < pattern.length()) return false;
		for (int i=0; i < pattern.length(); i++) {
			if (arr[start + i] != pattern.charAt(i)) return false;
		}
		return true;
	}
	
	public static void bench(Problem p, BiFunction<Problem, Integer, Result> f, String name) {
		
		int maxCores = Runtime.getRuntime().availableProcessors();
		for (int ncores=1; ncores<=maxCores; ncores *= 2) {

			for (int i=0; i< 30; i++) {
				long tseq = System.nanoTime();
				Result r = f.apply(p, ncores);
				tseq = System.nanoTime() - tseq;
			
				if (!r.compare(p.getSolution())) {
					System.out.println("Wrong result for " + name + ".");
					System.exit(1);
				}
				System.out.println(ncores + ";" + name + ";" + tseq);
			}
		}
	}
	
	public static void main(String[] args) {
		Problem p = (Runtime.getRuntime().availableProcessors() == 64) ? new P2Large() : new P1Small();
		Benchmark.bench(p, Benchmark::sequential, "seq");
		Benchmark.bench(p, Benchmark::parallel, "par");
	}

}
