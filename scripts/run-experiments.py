#!/usr/bin/env python

#run the experiments for all relevant values of k and one specific choice of algorithm

from __future__ import print_function
from subprocess import call

import sys
import os
import argparse
import glob
import subprocess as sp
import multiprocessing as mp

parser = argparse.ArgumentParser(description='A script for running our experiments on the real-world and random graphs.')


time_limit = 600
 
def work(in_file):
    """Defines the work unit on an input file"""
    # each line in the file contains problem number with parameters, graph file, parameter k and user provided upper bound
    split_line = in_file.split("//")
    # first entry is graph
    data_file = split_line[0]
    print(data_file)
    # k is second
    k = split_line[1].lstrip('/')
    print(k)
    #problem number is last
    problem = split_line[-1].lstrip('/')
    print(problem)
    #sp.call(["python3.6", "../fixCon.py", "../parametersk.pcs", data_file, k, problem, str(time_limit)])
    sp.call(["python3.6", "../fixCon.py", "../parametersp.pcs", data_file, k, problem, str(time_limit)])
    #sp.call(["python3.6", "../fixCon.py", "../parameterss.pcs", data_file, k, problem, str(time_limit)])
	#sp.call(["python", "../fixCon.py", "../parameters0.pcs", algo.strip(), in_file, str(time_limit)])
	#sp.call(["python", "../fixCon.py", "../parameters2.pcs", algo.strip(), in_file, str(time_limit)])
	#sp.call(["python", "../fixCon.py", "../parameters4.pcs", algo.strip(), in_file, str(time_limit)])
	#sp.call(["python", "../fixCon.py", "../parameters6.pcs", algo.strip(), in_file, str(time_limit)])
	#sp.call(["python", "../fixCon.py", "../parameters8.pcs", algo.strip(), in_file, str(time_limit)])
	#sp.call(["python", "../fixCon.py", "../parameters10.pcs", algo.strip(), in_file, str(time_limit)])
    #    for k in [3,4,5,6,7,8,9,10]:
    #	    for problem in ["1","2","3","4","5","6","7,2","8,2,5"]:
    #            sp.call(["python", "../fixCon.py", "../parameters.pcs", algo.strip(), problem, in_file, str(k), "inf", str(time_limit), "-1","../results/"+algo.strip()+"/"])
	# we removed pruning rule, so no inverse possible now     
	#for k in [1,2,3]:
        #    sp.call(["python", "../programm/enucon.py", algo.strip(), str(time_limit),str(k), in_file ,"../results/"+algo.strip()+"/","--inverse","True"])
            #sp.call(["rm", "../results/"+algo.strip()+"/"+in_file.split('/')[-1]+"."+str(k)+".subgraphs"])
        # sp.call(["sort", "../results/"+args.algo+"/"+os.path.basename(in_file)+"."+str(k)+".subgraphs",">", "tmp_file"],shell=True)
        # print("Checking")
        # for line in open("tmp_file"):
        #     print("Warning",line)
        # sp.call(["uniq", "-d", "tmp_file", ">","tmp_file2"],shell=True)
        # #sp.call(["rm", "tmp_file"])
        # for line in open("tmp_file2"):
        #     print("Warning",line)
        #sp.call(["rm", "tmp_file2"])
    return 0
 
if __name__ == '__main__':
    files = []
    #experiments for real-world instances 
    for line in open("../data/data_list-monotone.txt"):
        if not line.startswith("#"):
            files += [line.strip()]
    #experiments for random instances 
    #random_files = glob.glob("../data/random/*.edges")
    #for graph in random_files:
    #    files += [graph]
    print(files)
    
    #Set up the parallel task pool to use all available processors
    count = 24#mp.cpu_count()
    pool = mp.Pool(processes=count)
 
    #Run the jobs
	
    pool.map(work, files)


