import argparse

# script to create for an data set for one algorithm six files, three for small and three for large k (one for each of the categories dense, sparse and regular) such that the number of solved instances within some seconds is plotted
parser = argparse.ArgumentParser(description='A script to prepare the relevant data for the r plots. Output are all running times smaller than 600sec for the specific intervals of k.')
parser.add_argument('files', type=str, help='list of files, seperated by ",", which will be transformed')
args = parser.parse_args()

for file in args.files.split(','):
    this_file = open(file)
    new_file_d_4_10 = open(str(file)+"d-4-10", 'w+')
    new_file_d_11_20 = open(str(file)+"d-11-20", 'w+')
    new_file_s_4_10 = open(str(file)+"s-4-10", 'w+')
    new_file_s_11_20 = open(str(file)+"s-11-20", 'w+')
    new_file_r_4_10 = open(str(file)+"r-4-10", 'w+')
    new_file_r_11_20 = open(str(file)+"r-11-20", 'w+')
    s_d_4_10 = []
    s_d_11_20 = []
    s_s_4_10 = []
    s_s_11_20 = []
    s_r_4_10 = []
    s_r_11_20 = []
    for line in this_file:
        split_line = line.split()
	# first element is problem (1, 2 are dense; 3, 4, 5, 6 are sparse; 7,8 are regular)
	problem = int(split_line[0])
        # now ignore the problem specific parameters, they are either integers or intergs+','
        i = 1
        while split_line[i].isdigit() or split_line[i][0:-1].isdigit() or split_line[i][0:-2].isdigit():
            i += 1
        # now the values i+3 (k) and i+4 (time) are relevant
	# use the value of k to determine in which file the correspondign time has to be added
	# times of at least 600sec will be counted as 6000 sec
        k = int(split_line[i+3])
	if split_line[i+4][0].isdigit():
            time = float(split_line[i+4])
	else:
	    time = 600
        if time >= 600:
            time = 6000
        if time == 0:
            time = 0.01
	# write this time in the corresponding output file
	if 4 <= k <= 10:
	    if problem == 1 or problem == 2:
	        s_d_4_10.append(time)
	    if problem == 3 or problem == 4 or problem == 5 or problem == 6:
	        s_s_4_10.append(time)
	    if problem == 7 or problem == 8:
	        s_r_4_10.append(time)
	#if 8 <= k <= 11:
	#    new_file_8_11.write(new_line)
	#if 12 <= k <= 15:
	#    new_file_12_15.write(new_line)
	if 11 <= k <= 20:
	    if problem == 1 or problem == 2:
	        s_d_11_20.append(time)
	    if problem == 3 or problem == 4 or problem == 5 or problem == 6:
	        s_s_11_20.append(time)
	    if problem == 7 or problem == 8:
	        s_r_11_20.append(time)
    s_d_4_10.sort()
    s_d_11_20.sort()
    s_s_4_10.sort()
    s_s_11_20.sort()
    s_r_4_10.sort()
    s_r_11_20.sort()
    # now determine how many instances where solved in each second
    res_d_4_10 = []
    for i in range(1,6001):
	numb = 0
	if s_d_4_10 != []:
	    while s_d_4_10[numb]<= float(i)/10 and numb < len(s_d_4_10) - 1:
	        numb += 1
	#res_d_4_10.append(float(numb)/s_d_4_10.__len__())
	#res_d_4_10.append(float(numb)/756)
	    res_d_4_10.append(float(numb)/840)
    print(numb)
    res_d_11_20 = []
    for i in range(1,6001):
	numb = 0
	if s_d_11_20 != []:
	    while s_d_11_20[numb]<= float(i)/10 and numb < len(s_d_11_20) - 1:
	        numb += 1
	#res_d_11_20.append(float(numb)/s_d_11_20.__len__())
	#res_d_11_20.append(float(numb)/1080)
	    res_d_11_20.append(float(numb)/1200)
    print(numb)
    res_s_4_10 = []
    if s_s_4_10 != []:
        for i in range(1,6001):
	    numb = 0
	    while s_s_4_10[numb]<= float(i)/10 and numb < len(s_s_4_10) - 1:
	        numb += 1
	    #res_s_4_10.append(float(numb)/s_s_4_10.__len__())
	    res_s_4_10.append(float(numb)/1680)
    res_s_11_20 = []
    if s_s_11_20 != []:
        for i in range(1,6001):
	    numb = 0
	    while s_s_11_20[numb]<= float(i)/10 and numb < len(s_s_11_20) - 1:
	        numb += 1
	    #res_s_11_20.append(float(numb)/s_s_11_20.__len__())
	    res_s_11_20.append(float(numb)/2400)
    res_r_4_10 = []
    if s_r_4_10 != []:
        for i in range(1,6001):
	    numb = 0
	    while s_r_4_10[numb]<= float(i)/10 and numb < len(s_r_4_10) - 1:
	        numb += 1
	    #res_r_4_10.append(float(numb)/s_r_4_10.__len__())
	    res_r_4_10.append(float(numb)/840)
    res_r_11_20 = []
    if s_r_11_20 != []:
        for i in range(1,6001):
	    numb = 0
	    while s_r_11_20[numb]<= float(i)/10 and numb < len(s_r_11_20) - 1:
	        numb += 1
	    #res_r_11_20.append(float(numb)/s_r_11_20.__len__())
	    res_r_11_20.append(float(numb)/1200)
    res_d_4_10 = str(res_d_4_10)
    res_d_4_10 = res_d_4_10[1:len(res_d_4_10)-1]
    res_d_11_20 = str(res_d_11_20)
    res_d_11_20 = res_d_11_20[1:len(res_d_11_20) - 1]
    res_s_4_10 = str(res_s_4_10)
    res_s_4_10 = res_s_4_10[1:len(res_s_4_10)-1]
    res_s_11_20 = str(res_s_11_20)
    res_s_11_20 = res_s_11_20[1:len(res_s_11_20) - 1]
    res_r_4_10 = str(res_r_4_10)
    res_r_4_10 = res_r_4_10[1:len(res_r_4_10)-1]
    res_r_11_20 = str(res_r_11_20)
    res_r_11_20 = res_r_11_20[1:len(res_r_11_20) - 1]
    # write data in an output file
    this_file.close()
    new_file_d_4_10.write(res_d_4_10)
    new_file_d_11_20.write(res_d_11_20)
    new_file_d_4_10.close()
    new_file_d_11_20.close()
    new_file_s_4_10.write(res_s_4_10)
    new_file_s_11_20.write(res_s_11_20)
    new_file_s_4_10.close()
    new_file_s_11_20.close()
    new_file_r_4_10.write(res_r_4_10)
    new_file_r_11_20.write(res_r_11_20)
    new_file_r_4_10.close()
    new_file_r_11_20.close()
    #new_file_8_11.close()
    #new_file_12_15.close()
    

