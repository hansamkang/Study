#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <string>
#include <fstream>
#include <stdlib.h>
#include <Windows.h>

using namespace std;
#define CLEAR system("cls");
#define MEMORYSIZE 100
#define OUTPUTBUFFER 99
#define SWAP(x, y, t) ((t) = (x), (x) = (y), (y) = (t))

//static int accountId = 100;

struct Account
{
	int id;	//	���� ��ȭ
	char owner[12];	//	������ �̸�
	char branch[20];	//	���� ���� ����
	int balance;	//	�ܰ�
	double interest;	//	����
};

struct Block {
	struct Account record[85];	//	85*48 = 4080 byte
	int rec_num;	// ����� ���ڵ��� ���� 4 byte 
	char fill[12];	//	�߰����� ��� ���� ����
};

void sortRun(Block memory[], int n)
{
	Account temp;
	int minB, minA;
	for (int i = 0; i < n; i++) // ��� ��� ��ȸ
	{
		for (int j = 0; j < 85; j++)	// ��� account��ȸ
		{
			minA = i; minB = j;

			for (int k = i; k < n; k++)	//	
			{
				if (k == i)
				{
					for (int l = j; l < 85; l++)
					{
						if (memory[k].record[l].id < memory[minA].record[minB].id)
						{
							minA = k; minB = l;
						}
					}
				}
				else
				{
					for (int l = 0; l < 85; l++)
					{
						if (memory[k].record[l].id < memory[minA].record[minB].id)
						{
							minA = k; minB = l;
						}
					}
				}
			}
			//	Swap
			temp = memory[i].record[j];
			memory[i].record[j] = memory[minA].record[minB];
			memory[minA].record[minB] = temp;
		}
	}
}

void makeFile() {
	struct Block block;	// ���
	string fileName;	// �����̸�
	int option = 0;
	int accountIndex = 0;

	CLEAR;
	cout << "���� ���� ���� �̸� �Է�" << endl;
	cin >> fileName;

	ofstream fout(fileName, ios::out | ios::binary);
	if (!fout) {
		CLEAR;
		cout << "���� ������ �����߽��ϴ�. "<<endl;
		for (int i = 3; i > 0; i--)
		{
			cout << i << " �� �� ����ȭ������ ���� ���ϴ�." << endl;
			Sleep(1000);
		}
		return;
	}

	CLEAR;

	while (1)
	{
		cout << "1.custum block ����, 2.���� block ä���־� �Ϸ�" << endl;
		cin >> option;

		if (option == 1)
		{
			if (accountIndex > 84) {	// ���� ���� ���н�
				cout << "block �߰� �Ұ�, �ϷḦ �������ּ���";
			}

			else {
				CLEAR;
				//block.record[accountIndex].id = accountId++;	// id ���������� ���鋚
				block.record[accountIndex].id = 1000 + rand() % 10000;
				cout << "������ �̸� �Է�";
				cin >> block.record[accountIndex].owner;
				cout << "���� ���� �̸� �Է�";
				cin >> block.record[accountIndex].branch;
				cout << "�ܰ� ���� �Է�";
				cin >> block.record[accountIndex].balance;
				cout << "���� �Է�";
				cin >> block.record[accountIndex].interest;

				accountIndex++;
			}
		}
		else if (option == 2)
		{
			CLEAR;
			int blockNumber;	// �� ���� ����� ���������
			cout << "�� ���� ������� ����ǰǰ���?" << endl;
			cin >> blockNumber;

			block.rec_num = 85;

			for (int i = accountIndex; i < 85; i++)	// customblock�� �� ���ڵ� ���� ���������Ͽ� ä�� ����
			{
				//block.record[i].id = accountId++;	// id ���������� ������
				block.record[i].id = 1000 + rand() % 10000;
				for (int j = 0; j < 6; j++)
					block.record[i].owner[j] = 'A' + rand() % 20;
				for (int j = 0; j < 6; j++)
					block.record[i].branch[j] = 'A' + rand() % 20;
				block.record[i].balance = rand() % 1000;
				block.record[i].interest = rand() % 10;
			}

			accountIndex = 0;

			fout.write((char*)(&block), sizeof(block));	// ù block�� Ŀ���� ����� ������ ����

			for (int i = 0; i < blockNumber - 1; i++)	// ������ ���� ��������
			{
				for (int i = accountIndex; i < 85; i++)
				{
					//block.record[i].id = accountId++; // id ���������� ������
					block.record[i].id = 1000 + rand() % 10000;
					for (int j = 0; j < 6; j++)
						block.record[i].owner[j] = 'A' + rand() % 20;
					for (int j = 0; j < 6; j++)
						block.record[i].branch[j] = 'A' + rand() % 20;
					block.record[i].balance = rand() % 100;
					block.record[i].interest = rand() % 100;
				}
				accountIndex = 0;
				fout.write((char*)(&block), sizeof(block));	// �������� ������ ����
			}
			fout.close();
			return;
		}
		else {}
	}
}

void showFile() {
	Block block;
	string inputFileName;
	string outputFileName = "output.txt";
	int blockIndex = 0;

	CLEAR;
	cout << "���� ���� ���� �̸� �Է�" << endl;
	cin >> inputFileName;

	ifstream fin(inputFileName, ios::in | ios::binary);
	if (!fin) {
		CLEAR;
		cout << "���� ���⸦ �����߽��ϴ�. "<<endl;
		for (int i = 3; i > 0; i--)
		{
			cout << i << " �� �� ����ȭ������ ���� ���ϴ�." << endl;
			Sleep(1000);
		}
		return;
	}

	ofstream fout(outputFileName);
	if (!fout)
	{
		CLEAR;
		cout << "��� ���� ������ �����߽��ϴ�."<<endl;
		for (int i = 3; i > 0; i--)
		{
			cout << i << " �� �� ����ȭ������ ���� ���ϴ�." << endl;
			Sleep(1000);
		}
		return;
	}

	while (fin.read((char*)(&block), sizeof(block)))
	{
		fout << "///////////" << "��� �ѹ�: " << blockIndex << "///////////" << endl;
		for (int i = 0; i < 85; i++)
		{
			fout << "id : " << block.record[i].id << endl;
			fout << "owner : ";
			for (int j = 0; j < 6; j++) { fout << block.record[i].owner[j]; }
			fout << endl << "branch : ";
			for (int j = 0; j < 6; j++) { fout << block.record[i].branch[j]; }
			fout << endl;
			fout << "balance : "<< block.record[i].balance << endl;
			fout << "interest : " << block.record[i].interest << endl;
		}
		blockIndex++;
	}
	fin.close();
	fout.close();
}

void fileSort()
{
	Block memory[MEMORYSIZE];	//������ �޸�
	Block block;
	string run = "run";
	string dat = ".dat";
	string runFileName;
	string inputFileName;
	string outputFileName = "sorted.dat";
	ifstream runfin[98];

	int blockIndex = 0;
	int memoryIndex;
	int runIndex = 0;
	int outrecordIndex = 0;	// �޸��� ��� ������ ���ڵ� �ε��� 
	int accountIndex[99];
	bool dorun = true;
	CLEAR;
	cout << "���� ���� ���� �̸� �Է�" << endl;
	cin >> inputFileName;

	ifstream fin(inputFileName, ios::in | ios::binary);	// ���� ���� �ҷ���
	if (!fin) {
		CLEAR;
		cout << "���� ���⸦ �����߽��ϴ�. " << endl;
		for (int i = 3; i > 0; i--)
		{
			cout << i << " �� �� ����ȭ������ ���� ���ϴ�." << endl;
			Sleep(1000);
		}
		return;
	}

	while (1)	//	������ ����
	{

		for (memoryIndex = 0; memoryIndex < MEMORYSIZE; memoryIndex++)	// ���� ������ Memory ũ�⸸ŭ ��� ����
		{
			if (!fin.read((char*)(&memory[memoryIndex]), sizeof(block))) {	// ���� ���Ͽ��� ���̻� ���� ����� ����.
				fin.close();
				//dorun = false;
				break;
			}
		}
		if (memoryIndex == 0) { break; }
		sortRun(memory, memoryIndex);	// ����

		runFileName = run + to_string(runIndex) + dat;	//	run���� �̸� ����

		ofstream fout(runFileName, ios::out | ios::binary);	// run���� ����
		if (!fout)
		{
			CLEAR;
			cout << "��� ���� ������ �����߽��ϴ�."<<endl;
			for (int i = 3; i > 0; i--)
			{
				cout << i << " �� �� ����ȭ������ ���� ���ϴ�." << endl;
				Sleep(1000);
			}
			return;
		}

		for (int i = 0; i < memoryIndex; i++)	// run ���� ����
		{
			fout.write((char*)(&memory[i]), sizeof(block));
		}
		fout.close();
		runIndex++;
	}

	for (int i = 0; i < runIndex; i++)	// �����Ǿ��ִ� run ������ ������ŭ �޸� �Ҵ�
	{
		runFileName = run + to_string(i) + dat;
		runfin[i].open(runFileName, ios::in | ios::binary);
		runfin[i].read((char*)(&memory[i]), sizeof(block));
		accountIndex[i] = 0;
	}

	ofstream sfout(outputFileName, ios::out | ios::binary);	// sort.dat ���� ���� �� ����

	while (1){
		CLEAR;
		cout << "accountIndex ";
		for (int i = 0; i < runIndex; i++)
		{
			cout << "[" << accountIndex[i] << "], ";
		}
		cout << endl;
		
		int remainRun=0;	// ������ ���ڵ���� ���� ���� ����Ŵ

		for (int i = 0; i < runIndex+1; i++)
		{
			if (i == runIndex) // ��� memory���� ������ ������ ����.  
			{
				sfout.close();
				return;	// �Լ��� �����Ѵ�.
			}
			
			if(accountIndex[i] != -1)	
			{
				remainRun = i;
				break;
			}
		}

		int minA = remainRun, minB = accountIndex[remainRun]; // ������ ���ڵ尡 ���� �޸��� �ε����� ���ڵ��� �ε����� min�� ���� 

		for (int i = 0; i < runIndex; i++)	// �޸� ���� ���ڵ�� �� ���� ���� �� ã��
		{
			if (accountIndex[i] == -1) // memory[i]���� �� �̻� �߰��� ���ڵ尡 ����. 
			{
				runfin[i].close();
				continue;
			}

			if (memory[i].record[accountIndex[i]].id < memory[minA].record[minB].id)	// min���� �������� ������ min�� ��ü
			{
				minA = i; minB = accountIndex[i];
			}
		}

		memory[OUTPUTBUFFER].record[outrecordIndex] = memory[minA].record[minB];
		outrecordIndex++;
		if (outrecordIndex == 85)
		{
			outrecordIndex = 0;
			sfout.write((char*)(&memory[OUTPUTBUFFER]), sizeof(block));	// sorted.dat�� ����
		}

		accountIndex[minA]++;
		if (accountIndex[minA] == 85)
		{
			if (!runfin[minA].read((char*)(&memory[minA]), sizeof(block))) // ���̻� runi.dat���� �ҷ��� run�� ����
			{
				accountIndex[minA] = -1;
			}
			else {	// ��� ����ü
				accountIndex[minA] = 0;
			}
		}
	}
}

void searchSequence()
{
	Block block;

	string inputFileName;
	char searchName[12];

	bool exi = false;
	CLEAR;
	cout << "���� ���� ���� �̸� �Է�" << endl;
	cin >> inputFileName;
	
	ifstream fin(inputFileName, ios::in | ios::binary);	// ���� �ҷ���
	if (!fin) {
		CLEAR;
		cout << "���� ���⸦ �����߽��ϴ�. "<<endl;
		for (int i = 3; i > 0; i--)
		{
			cout << i << " �� �� ����ȭ������ ���� ���ϴ�." << endl;
			Sleep(1000);
		}
		return;
	}

	CLEAR;
	cout << "���� ���� ���� !" << endl;
	cout << "ã����� �̸��� �Է��ϼ���" << endl;
	cin >> searchName;

	while (fin.read((char*)(&block), sizeof(block)))
	{
		for (int i = 0; i < 85; i++) // ��� ��� ��ȸ
		{
			char * tempName =  block.record[i].owner;

			if (!strncmp(tempName,searchName, 6))
			{
				exi = true;
				cout << "id : " << block.record[i].id << endl;
				cout << "owner : ";
				for (int j = 0; j < 6; j++) { cout << block.record[i].owner[j]; }
				cout << endl << "branch : ";
				for (int j = 0; j < 6; j++) { cout << block.record[i].branch[j]; }
				cout << endl;
				cout << "balance : " << block.record[i].balance << endl;
				cout << "interest : " << block.record[i].interest << endl;
			}
		}
	}

	if (!exi) { CLEAR; cout << "���Ͻô� �̸��� ���� ���� �ʽ��ϴ�." << endl; }

	while (1)
	{
		int back=0;
		cout << "1 �Է½� ����ȭ������ ���ư��ϴ�." << endl;
		cin >> back;
		if (back == 1) { return; }
	}
}

void main()
{
	char end = 'N';
	while (end == 'N' || end == 'n')
	{
		CLEAR;
		short option;

		cout << "������ �������ּ���" << endl;
		cout << "1: �� ���� ����, 2: ���� ����[txt���Ϸ� ���], 3: ���� ���� , 4: �����˻� " << endl;
		cin >> option;

		switch (option) {
		case 1:
			makeFile();
			break;
		case 2:
			showFile();
			break;
		case 3:
			fileSort();
			break;
		case 4:
			searchSequence();
			break;
		}

		cout << "���α׷��� ���� �Ͻðڽ��ϱ�" << endl;

	}
}