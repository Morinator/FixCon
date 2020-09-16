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
    sp.call(["java", "-jar", "../out/artifacts/FixCon_jar/FixCon.jar", data_file, k, problem, str(time_limit)])
    return 0
 
if __name__ == '__main__':
    files = []
    #experiments for real-world instances 
    for line in open("../scripts/data_list-all.txt"):
        if not line.startswith("#"):
            files += [line.strip()]
    #experiments for random instances 
    #random_files = glob.glob("../data/random/*.edges")
    #for graph in random_files:
    #    files += [graph]
    print(files)
    
    #Set up the parallel task pool to use all available processors
    count = 12#mp.cpu_count()
    pool = mp.Pool(processes=count)
 
    #Run the jobs
	
    pool.map(work, files)


