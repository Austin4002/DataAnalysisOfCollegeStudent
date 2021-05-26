import pandas as pd

if __name__ == '__main__':
    # 导入数据集
    df = pd.read_excel('E:/testFile/stuCheck.xlsx', index_col=0)
    # 取出列名
    col = df.columns.values
    # 去除列名左右的空格
    df.columns = [x.strip() for x in col]
    # 删除重复数据
    df.drop_duplicates(inplace=True)

    # 删除数据之后重置索引，生成一个1到shape[0]+1的序列
    df.index = range(1, df.shape[0] + 1)
    # df.index = range(df.shape[0])

    # 将处理过的数据集输出到excel中
    writer = pd.ExcelWriter('../../../../../resources/static/stuCheckResult.xlsx')
    df.to_excel(writer)
    writer.save()
    print('数据导出成功')