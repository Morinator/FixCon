import argparse


parser = argparse.ArgumentParser(description='A script to create a txt file with data sets from a list of graphs.')
parser.add_argument('graph_list', type=argparse.FileType('r'), help='list of graphs')
args = parser.parse_args()

filename_data = "../data/data_list-all.txt"
#filename_data = "../data/data_list.txt"
data_file = open(filename_data, 'w+')
for line in args.graph_list:
    for k in [4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]:
	#for problem in ["8,3,5"]:        
	#for problem in ["1"]:
        for problem in ["1","2","3","4","5","6","8,3,5"]:
            data_file.write(line[:-1]+"///"+str(k)+"///"+problem+"\n")
    for k in [5,7,9,11,13,15,17,19]:
        for problem in ["7,4"]:
            data_file.write(line[:-1]+"///"+str(k)+"///"+problem+"\n")
    for k in [4,6,8,10,12,14,16,18,20]:
        for problem in ["7,3"]:
            data_file.write(line[:-1]+"///"+str(k)+"///"+problem+"\n")
data_file.close()
