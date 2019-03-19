using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace WpfApp1
{
    /// <summary>
    /// MainWindow.xaml에 대한 상호 작용 논리
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private void BtnYear_Click(object sender, RoutedEventArgs e)
        {
            if (this.TextBox2.Text != "" || this.TextBox2.Text != "")
            { 
                MessageBox.Show("당신의 입력한 데이터는 " + this.TextBox1.Text + " 와 " + this.TextBox2.Text + "살 입니다");
            }
            else
            {
                MessageBox.Show("데이터를 입력하십시오");
            }
        }
        
    }

    
}
