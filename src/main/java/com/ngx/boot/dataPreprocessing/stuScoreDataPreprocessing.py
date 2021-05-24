import pandas as pd

if __name__ == '__main__':
    # 导入数据集
    df = pd.read_excel('E:/testFile/stuScore.xlsx', index_col=0)
    # 取出列名
    col = df.columns.values
    # 去除列名左右的空格
    df.columns = [x.strip() for x in col]
    # 删除重复数据
    df.drop_duplicates(inplace=True)

    # 使用z-score对数据进行标准化
    sta1 = (df['科目一成绩'] - df['科目一成绩'].mean()) / df['科目一成绩'].std()
    sta2 = (df['科目二成绩'] - df['科目二成绩'].mean()) / df['科目二成绩'].std()
    sta3 = (df['科目三成绩'] - df['科目三成绩'].mean()) / df['科目三成绩'].std()
    # 使用3σ探测方法检测异常值
    # 获取分数大于3个标准差之外的数据索引
    delIndex1 = df[sta1.abs() > 3].index
    delIndex2 = df[sta2.abs() > 3].index
    delIndex3 = df[sta3.abs() > 3].index
    # 删除数据
    df.drop(delIndex1, inplace=True)
    df.drop(delIndex2, inplace=True)
    df.drop(delIndex3, inplace=True)
    # 处理缺失值
    # 先处理分数的缺失值
    # 采用保留2位小数的均值填充
    score1 = round(df['科目一成绩'].mean(), 2)
    score2 = round(df['科目二成绩'].mean(), 2)
    score3 = round(df['科目三成绩'].mean(), 2)
    df['科目一成绩'].fillna(score1, inplace=True)
    df['科目二成绩'].fillna(score2, inplace=True)
    df['科目三成绩'].fillna(score3, inplace=True)
    # 处理其余文本缺失值
    # 获取其余空缺值索引
    nullDelIndex = df[df.isnull().values == True].index
    # 删除数据
    df.drop(nullDelIndex, inplace=True)

    # 删除数据之后重置索引，生成一个1到shape[0]+1的序列
    df.index = range(1, df.shape[0] + 1)
    # df.index = range(df.shape[0])

    # 将处理过的数据集输出到excel中
    writer = pd.ExcelWriter('../../../../../resources/static/stuScoreResult.xlsx')
    df.to_excel(writer)
    writer.save()
    print('数据导出成功')