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
	int id;	//	계좌 번화
	char owner[12];	//	계좌주 이름
	char branch[20];	//	계좌 개설 지점
	int balance;	//	잔고
	double interest;	//	이율
};

struct Block {
	struct Account record[85];	//	85*48 = 4080 byte
	int rec_num;	// 저장된 레코드의 개수 4 byte 
	char fill[12];	//	추가적인 블록 정보 저장
};

void sortRun(Block memory[], int n)
{
	Account temp;
	int minB, minA;
	for (int i = 0; i < n; i++) // 모든 블록 순회
	{
		for (int j = 0; j < 85; j++)	// 모든 account순회
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
	struct Block block;	// 블록
	string fileName;	// 파일이름
	int option = 0;
	int accountIndex = 0;

	CLEAR;
	cout << "새로 만들 파일 이름 입력" << endl;
	cin >> fileName;

	ofstream fout(fileName, ios::out | ios::binary);
	if (!fout) {
		CLEAR;
		cout << "파일 생성을 실패했습니다. "<<endl;
		for (int i = 3; i > 0; i--)
		{
			cout << i << " 초 후 메인화면으로 돌아 갑니다." << endl;
			Sleep(1000);
		}
		return;
	}

	CLEAR;

	while (1)
	{
		cout << "1.custum block 생성, 2.랜덤 block 채워넣어 완료" << endl;
		cin >> option;

		if (option == 1)
		{
			if (accountIndex > 84) {	// 파일 열기 실패시
				cout << "block 추가 불가, 완료를 선택해주세요";
			}

			else {
				CLEAR;
				//block.record[accountIndex].id = accountId++;	// id 순차적으로 만들떄
				block.record[accountIndex].id = 1000 + rand() % 10000;
				cout << "소유주 이름 입력";
				cin >> block.record[accountIndex].owner;
				cout << "개설 지점 이름 입력";
				cin >> block.record[accountIndex].branch;
				cout << "잔고 설정 입력";
				cin >> block.record[accountIndex].balance;
				cout << "이율 입력";
				cin >> block.record[accountIndex].interest;

				accountIndex++;
			}
		}
		else if (option == 2)
		{
			CLEAR;
			int blockNumber;	// 몇 개의 블록을 만들것인지
			cout << "몇 개의 블록으로 만드실건가요?" << endl;
			cin >> blockNumber;

			block.rec_num = 85;

			for (int i = accountIndex; i < 85; i++)	// customblock의 빈 레코드 들을 랜덤생성하여 채워 넣음
			{
				//block.record[i].id = accountId++;	// id 순차적으로 생성시
				block.record[i].id = 1000 + rand() % 10000;
				for (int j = 0; j < 6; j++)
					block.record[i].owner[j] = 'A' + rand() % 20;
				for (int j = 0; j < 6; j++)
					block.record[i].branch[j] = 'A' + rand() % 20;
				block.record[i].balance = rand() % 1000;
				block.record[i].interest = rand() % 10;
			}

			accountIndex = 0;

			fout.write((char*)(&block), sizeof(block));	// 첫 block인 커스텀 블록의 데이터 저장

			for (int i = 0; i < blockNumber - 1; i++)	// 나머지 블럭을 랜덤생성
			{
				for (int i = accountIndex; i < 85; i++)
				{
					//block.record[i].id = accountId++; // id 순차적으로 생성시
					block.record[i].id = 1000 + rand() % 10000;
					for (int j = 0; j < 6; j++)
						block.record[i].owner[j] = 'A' + rand() % 20;
					for (int j = 0; j < 6; j++)
						block.record[i].branch[j] = 'A' + rand() % 20;
					block.record[i].balance = rand() % 100;
					block.record[i].interest = rand() % 100;
				}
				accountIndex = 0;
				fout.write((char*)(&block), sizeof(block));	// 랜덤블럭의 데이터 저장
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
	cout << "열고 싶은 파일 이름 입력" << endl;
	cin >> inputFileName;

	ifstream fin(inputFileName, ios::in | ios::binary);
	if (!fin) {
		CLEAR;
		cout << "파일 열기를 실패했습니다. "<<endl;
		for (int i = 3; i > 0; i--)
		{
			cout << i << " 초 후 메인화면으로 돌아 갑니다." << endl;
			Sleep(1000);
		}
		return;
	}

	ofstream fout(outputFileName);
	if (!fout)
	{
		CLEAR;
		cout << "출력 파일 생성에 실패했습니다."<<endl;
		for (int i = 3; i > 0; i--)
		{
			cout << i << " 초 후 메인화면으로 돌아 갑니다." << endl;
			Sleep(1000);
		}
		return;
	}

	while (fin.read((char*)(&block), sizeof(block)))
	{
		fout << "///////////" << "블록 넘버: " << blockIndex << "///////////" << endl;
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
	Block memory[MEMORYSIZE];	//가상의 메모리
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
	int outrecordIndex = 0;	// 메모리의 출력 버퍼의 레코드 인덱스 
	int accountIndex[99];
	bool dorun = true;
	CLEAR;
	cout << "열고 싶은 파일 이름 입력" << endl;
	cin >> inputFileName;

	ifstream fin(inputFileName, ios::in | ios::binary);	// 원본 파일 불러옴
	if (!fin) {
		CLEAR;
		cout << "파일 열기를 실패했습니다. " << endl;
		for (int i = 3; i > 0; i--)
		{
			cout << i << " 초 후 메인화면으로 돌아 갑니다." << endl;
			Sleep(1000);
		}
		return;
	}

	while (1)	//	런파일 생성
	{

		for (memoryIndex = 0; memoryIndex < MEMORYSIZE; memoryIndex++)	// 원본 파일을 Memory 크기만큼 떼어서 저장
		{
			if (!fin.read((char*)(&memory[memoryIndex]), sizeof(block))) {	// 원본 파일에서 더이상 읽을 블록이 없다.
				fin.close();
				//dorun = false;
				break;
			}
		}
		if (memoryIndex == 0) { break; }
		sortRun(memory, memoryIndex);	// 정렬

		runFileName = run + to_string(runIndex) + dat;	//	run파일 이름 생성

		ofstream fout(runFileName, ios::out | ios::binary);	// run파일 생성
		if (!fout)
		{
			CLEAR;
			cout << "출력 파일 생성에 실패했습니다."<<endl;
			for (int i = 3; i > 0; i--)
			{
				cout << i << " 초 후 메인화면으로 돌아 갑니다." << endl;
				Sleep(1000);
			}
			return;
		}

		for (int i = 0; i < memoryIndex; i++)	// run 파일 쓰기
		{
			fout.write((char*)(&memory[i]), sizeof(block));
		}
		fout.close();
		runIndex++;
	}

	for (int i = 0; i < runIndex; i++)	// 생성되어있는 run 파일의 갯수만큼 메모리 할당
	{
		runFileName = run + to_string(i) + dat;
		runfin[i].open(runFileName, ios::in | ios::binary);
		runfin[i].read((char*)(&memory[i]), sizeof(block));
		accountIndex[i] = 0;
	}

	ofstream sfout(outputFileName, ios::out | ios::binary);	// sort.dat 파일 생성 및 열기

	while (1){
		CLEAR;
		cout << "accountIndex ";
		for (int i = 0; i < runIndex; i++)
		{
			cout << "[" << accountIndex[i] << "], ";
		}
		cout << endl;
		
		int remainRun=0;	// 정렬할 레코드들이 남은 런을 가리킴

		for (int i = 0; i < runIndex+1; i++)
		{
			if (i == runIndex) // 모든 memory에서 정렬할 런들이 없다.  
			{
				sfout.close();
				return;	// 함수를 종료한다.
			}
			
			if(accountIndex[i] != -1)	
			{
				remainRun = i;
				break;
			}
		}

		int minA = remainRun, minB = accountIndex[remainRun]; // 정렬할 레코드가 남은 메모리의 인덱스와 레코드의 인덱스로 min을 설정 

		for (int i = 0; i < runIndex; i++)	// 메모리 내부 레코드들 중 가장 작은 값 찾기
		{
			if (accountIndex[i] == -1) // memory[i]에는 더 이상 추가할 레코드가 없다. 
			{
				runfin[i].close();
				continue;
			}

			if (memory[i].record[accountIndex[i]].id < memory[minA].record[minB].id)	// min보다 작은값이 잇으면 min을 교체
			{
				minA = i; minB = accountIndex[i];
			}
		}

		memory[OUTPUTBUFFER].record[outrecordIndex] = memory[minA].record[minB];
		outrecordIndex++;
		if (outrecordIndex == 85)
		{
			outrecordIndex = 0;
			sfout.write((char*)(&memory[OUTPUTBUFFER]), sizeof(block));	// sorted.dat에 저장
		}

		accountIndex[minA]++;
		if (accountIndex[minA] == 85)
		{
			if (!runfin[minA].read((char*)(&memory[minA]), sizeof(block))) // 더이상 runi.dat에서 불러올 run이 없음
			{
				accountIndex[minA] = -1;
			}
			else {	// 블록 정상교체
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
	cout << "열고 싶은 파일 이름 입력" << endl;
	cin >> inputFileName;
	
	ifstream fin(inputFileName, ios::in | ios::binary);	// 파일 불러옴
	if (!fin) {
		CLEAR;
		cout << "파일 열기를 실패했습니다. "<<endl;
		for (int i = 3; i > 0; i--)
		{
			cout << i << " 초 후 메인화면으로 돌아 갑니다." << endl;
			Sleep(1000);
		}
		return;
	}

	CLEAR;
	cout << "파일 열기 성공 !" << endl;
	cout << "찾고싶은 이름을 입력하세요" << endl;
	cin >> searchName;

	while (fin.read((char*)(&block), sizeof(block)))
	{
		for (int i = 0; i < 85; i++) // 모든 블록 순회
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

	if (!exi) { CLEAR; cout << "원하시는 이름이 존재 하지 않습니다." << endl; }

	while (1)
	{
		int back=0;
		cout << "1 입력시 메인화면으로 돌아갑니다." << endl;
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

		cout << "동작을 선택해주세요" << endl;
		cout << "1: 새 파일 생성, 2: 파일 보기[txt파일로 출력], 3: 파일 정렬 , 4: 순차검색 " << endl;
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

		cout << "프로그램을 종료 하시겠습니까" << endl;

	}
}